package br.edu.ifpb.pweb2.bitbank.util;

import org.mindrot.jbcrypt.BCrypt;

// Cifra as senhas dos correntistas antes de salvá-las no banco,
// para que elas não sejam gravadas abertas
public abstract class PasswordUtil {

    public static String hashPassword(String plainTextPassword) {
        return BCrypt.hashpw(plainTextPassword, BCrypt.gensalt());
    };

    public static boolean checkPass(String plainPassword, String hashedPassword) {
        if (BCrypt.checkpw(plainPassword, hashedPassword)) {
            return true;
        }
        else {
            return false;
        }
    }
}
