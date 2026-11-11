import io.quarkus.elytron.security.common.BcryptUtil;

public class GenerateHash {
    public static void main(String[] args) {
        System.out.println(BcryptUtil.bcryptHash("holamundo123", 10));
    }
}