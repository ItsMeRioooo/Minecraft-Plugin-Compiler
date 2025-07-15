import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CompilerAppTest {

    @Test
    void testCompileFromGitHubLink() {
        CompilerApp compilerApp = new CompilerApp();
        String result = compilerApp.compile("https://github.com/example/minecraft-plugin");
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