package models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class TestUserRegistration {

    @Test
    public void testHashPassword() {
        // Define test cases with input passwords and their expected hash values
        String[] passwords = {"password123", "securePassword", "test123"};
        String[] expectedHashes = {
                "ef92b778bafe771e89245b89ecbc08a44a4e166c06659911881f383d4473e94f",
                "debe062ddaaf9f8b06720167c7b65c778c934a89ca89329dcb82ca79d19e17d2",
                "ecd71870d1963316a97e3ac3408c9835ad8cf0f3c1bc703527c30265534f75ae"
        };

        for (int i = 0; i < passwords.length; i++) {
            User user = new User();
            String password = passwords[i];
            String expectedHash = expectedHashes[i];
            String actualHash = User.hashPassword(password);
            assertEquals(expectedHash, actualHash, "Hashing for password '" + password + "' failed.");
        }
    }


}


