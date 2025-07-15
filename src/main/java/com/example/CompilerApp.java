package com.example;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.UUID;

public class CompilerApp {

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Usage: java CompilerApp <GitHub link or local path>");
            return;
        }

        String sourcePath = args[0];
        try {
            String compiledJarPath = new CompilerApp().compile(sourcePath);
            System.out.println("Compiled JAR file created at: " + compiledJarPath);
        } catch (Exception e) {
            System.err.println("Error during compilation: " + e.getMessage());
        }
    }

    public String compile(String source) {
        if (source == null || source.trim().isEmpty()) {
            throw new IllegalArgumentException("Source path cannot be empty.");
        }

        boolean isGitHub = source.startsWith("https://github.com/");
        File projectDir;

        try {
            if (isGitHub) {
                String tempDir = Files.createTempDirectory("plugin-src-").toString();
                String repoName = source.substring(source.lastIndexOf('/') + 1);
                String cloneDir = tempDir + "/" + repoName;
                Process clone = new ProcessBuilder("git", "clone", source, cloneDir)
                        .inheritIO().start();
                if (clone.waitFor() != 0) {
                    throw new IllegalArgumentException("Invalid GitHub link provided.");
                }
                projectDir = new File(cloneDir);
            } else {
                projectDir = new File(source);
                if (!projectDir.exists() || !projectDir.isDirectory()) {
                    throw new IllegalArgumentException("Invalid local path provided.");
                }
            }

            File gradleFile = new File(projectDir, "build.gradle");
            File mavenFile = new File(projectDir, "pom.xml");
            File jarFile = null;

            if (gradleFile.exists()) {
                Process gradle = new ProcessBuilder("sh", "-c", "./gradlew build || gradle build")
                        .directory(projectDir)
                        .inheritIO().start();
                if (gradle.waitFor() != 0) {
                    throw new RuntimeException("Gradle build failed.");
                }
                File libsDir = new File(projectDir, "build/libs");
                jarFile = findJar(libsDir);
            } else if (mavenFile.exists()) {
                Process maven = new ProcessBuilder("mvn", "package")
                        .directory(projectDir)
                        .inheritIO().start();
                if (maven.waitFor() != 0) {
                    throw new RuntimeException("Maven build failed.");
                }
                File targetDir = new File(projectDir, "target");
                jarFile = findJar(targetDir);
            } else {
                throw new RuntimeException("No build.gradle or pom.xml found.");
            }

            if (jarFile == null) {
                throw new RuntimeException("No JAR file produced.");
            }

            File jarsDir = new File("Jars");
            if (!jarsDir.exists()) jarsDir.mkdirs();
            File destJar = new File(jarsDir, jarFile.getName());
            Files.copy(jarFile.toPath(), destJar.toPath(), StandardCopyOption.REPLACE_EXISTING);

            return destJar.getAbsolutePath();

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Compilation failed: " + e.getMessage(), e);
        }
    }

    private File findJar(File dir) {
        if (!dir.exists()) return null;
        File[] jars = dir.listFiles((d, name) -> name.endsWith(".jar"));
        return (jars != null && jars.length > 0) ? jars[0] : null;
    }
}