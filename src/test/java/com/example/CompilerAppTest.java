import com.example.CompilerApp;
import io.github.cdimascio.dotenv.Dotenv;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CompilerAppTest {

    private static final Dotenv dotenv = Dotenv.load();

    @Test
    void testCompileFromGitHubLink() {
        String githubLink = dotenv.get("GITHUB_LINK");
        CompilerApp compilerApp = new CompilerApp();
        String result = compilerApp.compile(githubLink);
        assertTrue(result.endsWith(".jar"), "Compilation should produce a JAR file.");
    }

    @Test
    void testCompileFromLocalContents() {
        CompilerApp compilerApp = new CompilerApp();
        String result = compilerApp.compile("/path/to/local/plugin");
        assertTrue(result.endsWith(".jar"), "Compilation should produce a JAR file.");
    }

    @Test
    void testInvalidGitHubLink() {
        CompilerApp compilerApp = new CompilerApp();
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            compilerApp.compile("invalid-link");
        });
        assertEquals("Invalid GitHub link provided.", exception.getMessage());
    }

    @Test
    void testEmptySourcePath() {
        CompilerApp compilerApp = new CompilerApp();
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            compilerApp.compile("");
        });
        assertEquals("Source path cannot be empty.", exception.getMessage());
    }
}