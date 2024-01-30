package cn.cnowse.util;

import java.security.SecureRandom;

import org.apache.tomcat.util.buf.HexUtils;

/**
 * 密码加密解密
 *
 * @author Jeong Geol 2024-01-29
 */
public class PasswordEncoder {

    private static final int SALT_LENGTH = 8; // bytes count
    private static final int DIGEST_TIMES = 10;
    private final SecureRandom random = new SecureRandom();

    public String encodePreEncoded(String preEncodedPassword) {
        final byte[] salt = this.generateSalt();
        byte[] bs = preEncodedPassword.getBytes();
        byte[] digested = this.encodeSaltAndRaw(salt, bs);
        return HexUtils.toHexString(concat(salt, digested));
    }

    public boolean matchesPreEncoded(String preEncodedPassword, String encodedPassword) {
        if (encodedPassword == null) {
            return false;
        }
        byte[] saltAndDigested = HexUtils.fromHexString(encodedPassword);
        byte[] salt = new byte[SALT_LENGTH];
        System.arraycopy(saltAndDigested, 0, salt, 0, SALT_LENGTH);
        byte[] encoded = this.encodeSaltAndRaw(salt, preEncodedPassword.getBytes());
        if (encoded.length == saltAndDigested.length - SALT_LENGTH) {
            for (int i = 0; i < encoded.length; i++) {
                if (encoded[i] != saltAndDigested[i + SALT_LENGTH]) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    public String encode(String rawPassword) {
        return this.encodePreEncoded(Digests.sha256(rawPassword));
    }

    public boolean matches(String rawPassword, String encodedPassword) {
        return this.matchesPreEncoded(Digests.sha256(rawPassword), encodedPassword);
    }

    private byte[] concat(byte[] bs1, byte[] bs2) {
        byte[] bs = new byte[bs1.length + bs2.length];
        System.arraycopy(bs1, 0, bs, 0, bs1.length);
        System.arraycopy(bs2, 0, bs, bs1.length, bs2.length);
        return bs;
    }

    private byte[] encodeSaltAndRaw(byte[] salt, byte[] bs) {
        byte[] toDigest = concat(salt, bs);
        return Digests.sha256(toDigest, DIGEST_TIMES);
    }

    private byte[] generateSalt() {
        byte[] bs = new byte[SALT_LENGTH];
        random.nextBytes(bs);
        return bs;
    }

}
