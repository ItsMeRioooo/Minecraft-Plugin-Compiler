# Minecraft Plugin Compiler

## Overview
The Minecraft Plugin Compiler is a tool designed to compile Minecraft plugins from a specified GitHub repository or local source files into a Java JAR file. This project simplifies plugin development and deployment for Minecraft server administrators and developers.

## Features
- Compile Minecraft plugins from GitHub links or local directories.
- Automatically generates JAR files in the `Jars` folder, ready for deployment.
- Error handling and logging for a smooth user experience.

## Requirements
- Java Development Kit (JDK) 8 or higher
- Gradle 6.0 or higher (or use the included Gradle wrapper)
- Internet connection (for fetching GitHub repositories)
- `git` installed on your system

## Installation
1. Clone the repository:
   ```sh
   git clone <repository-url>
   cd Minecraft-Plugin-Compiler
   ```
2. **Before building or running the project, create a `.env` file in the project root and add the GitHub link of the plugin you want to compile:**
   ```
   GITHUB_LINK=https://github.com/yourusername/your-minecraft-plugin
   ```
   Replace the value with the actual GitHub repository link you want to compile.

3. Build the project using Gradle:
   ```sh
   ./gradlew build
   ```

## Usage

To compile a Minecraft plugin from a GitHub link or local path, run:

```sh
java -cp build/classes/java/main com.example.CompilerApp <github-link-or-local-path>
```

**Example:**
```sh
java -cp build/classes/java/main com.example.CompilerApp "https://github.com/PlayPro/CoreProtect"
```

- The compiled JAR will be placed in the `Jars` folder in your project root.

## Contributing
Contributions are welcome! Please submit a pull request or open an issue for any enhancements or bug fixes.

## License
This project is licensed under the MIT License. See the