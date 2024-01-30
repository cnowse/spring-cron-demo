package cn.cnowse.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import cn.cnowse.web.UtilException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Digests 是一个术语，通常用于密码学和数据安全领域。在密码学中，"digest"
 * 是指对数据进行哈希运算后生成的固定长度的数据块，通常用于验证数据的完整性和唯一性，而不是用于加密。Digests 也被称为哈希值或摘要。<br/>
 * 主要特征包括：<br/>
 * 1.不可逆性（One-way）：无法从哈希值反推出原始数据，即使知道哈希算法和哈希值。 固定长度：不同大小的输入数据都会生成相同长度的哈希值。<br/>
 * 2.唯一性：理想情况下，不同的输入数据应该生成不同的哈希值。<br/>
 * 3.抗碰撞性（CollisionResistance）：很难找到两个不同的输入数据，它们生成相同的哈希值。 常见的哈希算法包括
 * MD5、SHA-1、SHA-256 等。在信息安全领域，Digests 通常用于验证密码的一致性、验证数据的完整性、数字签名等方面。
 *
 * @author Jeong Geol 2024-01-29
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class Digests {

    public static final String SHA_256 = "SHA-256";
    public static final String SHA_1 = "SHA1";
    private static final char[] HEX_CHARS =
            new char[] {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    public static String md5(String str) {
        if (str == null) {
            return null;
        }
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] digest = md5.digest(str.getBytes());
            return hex(digest);
        } catch (Exception e) {
            throw new UtilException(e);
        }
    }

    public static String sha256(String s) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance(SHA_256);
            md.update(s.getBytes());
            byte[] bytes = md.digest();
            return hex(bytes);
        } catch (NoSuchAlgorithmException e) {
            throw new UtilException(e);
        }
    }

    public static byte[] sha256(byte[] bs) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance(SHA_256);
            md.update(bs);
            return md.digest();
        } catch (NoSuchAlgorithmException e) {
            throw new UtilException(e);
        }
    }

    /**
     * 进行多次 sha256摘要
     */
    public static byte[] sha256(byte[] bs, int times) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance(SHA_256);
            byte[] value = bs;
            for (int i = 0; i < times; i++) {
                md.reset();
                md.update(value);
                value = md.digest();
            }
            return value;
        } catch (NoSuchAlgorithmException e) {
            throw new UtilException(e);
        }
    }

    public static String sha1(String s) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance(SHA_1);
            md.update(s.getBytes());
            byte[] bytes = md.digest();
            return hex(bytes);
        } catch (NoSuchAlgorithmException e) {
            throw new UtilException(e);
        }
    }

    public static byte[] sha1(byte[] s) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance(SHA_1);
            md.update(s);
            return md.digest();
        } catch (NoSuchAlgorithmException e) {
            throw new UtilException(e);
        }
    }

    public static String sha1hex(byte[] s) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance(SHA_1);
            md.update(s);
            return hex(md.digest());
        } catch (NoSuchAlgorithmException e) {
            throw new UtilException(e);
        }
    }

    /**
     * 先计算 sha-256再 base64 编码（标准编码）
     */
    public static String base64sha256(String s) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance(SHA_256);
            md.update(s.getBytes());
            byte[] bytes = md.digest();
            return Base64.getEncoder().encodeToString(bytes);
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

    public static String hex(byte[] bytes) {
        return hex(bytes, 0, bytes.length);
    }

    public static String hex(byte[] bytes, int offset, int length) {
        if (offset < 0 || length < 0 || (offset + length > bytes.length)) {
            throw new UtilException();
        }
        StringBuilder sb = new StringBuilder(length * 2);
        byte b;
        for (int i = offset; i < offset + length; i++) {
            b = bytes[i];
            sb.append(HEX_CHARS[(b >> 4) & 0x0F]);
            sb.append(HEX_CHARS[b & 0x0F]);
        }
        return sb.toString();
    }

}
