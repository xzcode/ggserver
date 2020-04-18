package com.xzcode.ggserver.core.common.encryption.impl;

import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;

import javax.crypto.Cipher;

import org.apache.commons.codec.binary.Base64;

import com.xzcode.ggserver.core.common.encryption.IEncipher;

public class RSAEncipher implements IEncipher {

	private static final int PART_LEN = 128;
	String privateKey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAJ0ZUE4ZlcJsJZlSJ5F541F8o2qS13EqsYJim95xl5FUQrkPLPHgzLgtibAuIggh/8MTH3PRTKgVdDzCByfVuc5/Ub9pP6c6EHoOMQoGtjeTQ99mc4xLiEDwL6rq48Lo2+N1TNA6wH0SZfGr2rl40BLrJy8GjUMeArCoZCiTY5DTAgMBAAECgYAVAXx+ZOdkbsd8P8Lwrcaab7r+FhJenGHN2FeVH8Uvdn/RdNkCopddDSG7AAAm7DzhnZ34A4VneC7prNv+FJLadroeqztwsVICNWlRlDdoWEEE7gA5OWge07BsLTUtvxiWpZ2KDPdnq/pCIYN7LH4oW6PHUZFS0QOYalZG5qfY+QJBAN6EfRKgSoZcYphFF2WZWsGEzZOPLbpK+4+gcvm3Ihz5CtMvWCUl1OtvgqagDvMoBGfjnjCtQncJiCrNiElrlY0CQQC0vNvRK8P6wpDyxArGApVBa3vXAbj+on1ir0yT6ee/osI/5KD/gVbPWkh3ByvhnJR+LT9oGajEd5Uq7CgxdzffAkEA0iidnB7p5BaTRC9VFq8NKWLNaoU68gzppNAsZy8Qt/56u9SmUod1nls2MTtQg1UTPC+dc2ngMV8+TPbLtlQ27QJAEPA6MapGXbPqXbYdxFztnAn0uzvAGK0lzx/ar2oWfBFG3zIQHKIEfr5ZWD5l0GkaSZD4BkuRU4hZhIJJaglgRQJBAIbnjBHd4SUGaqGROx5XXc4Z5luNLVDWr0cvd+eHkeDBxQUn7OqZboJGS3PafsJLO48EuGo6UsGiHCFtVt2xO1s=";
	String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCdGVBOGZXCbCWZUieReeNRfKNqktdxKrGCYpvecZeRVEK5Dyzx4My4LYmwLiIIIf/DEx9z0UyoFXQ8wgcn1bnOf1G/aT+nOhB6DjEKBrY3k0PfZnOMS4hA8C+q6uPC6NvjdUzQOsB9EmXxq9q5eNAS6ycvBo1DHgKwqGQok2OQ0wIDAQAB";
	
	Cipher encryptCipher;
	Cipher decryptCipher;
	
	
	public RSAEncipher() {
		try {
			byte[] privateKeyDecoded = Base64.decodeBase64(privateKey);
			PKCS8EncodedKeySpec priKeySpec = new PKCS8EncodedKeySpec(privateKeyDecoded);
			RSAPrivateKey priKey = (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(priKeySpec);
			encryptCipher = Cipher.getInstance("RSA");
			encryptCipher.init(Cipher.ENCRYPT_MODE, priKey);
			
			
			byte[] publicKeyDecoded = Base64.decodeBase64(publicKey);
			X509EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(publicKeyDecoded);
			RSAPublicKey pubKey = (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(pubKeySpec);
			decryptCipher = Cipher.getInstance("RSA");
			decryptCipher.init(Cipher.DECRYPT_MODE, pubKey);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public byte[] encrypt(byte[] data) {

		// 使用私钥加密
		try {
			
			int dataLen = data.length;
			int times = dataLen % 117 == 0 ? dataLen / 117 : dataLen / 117 + 1;
			byte[] dist = new byte[PART_LEN * times];
			int distOffset = 0;
			for (int i = 0; i < times; i++) {
				byte[] copyOfRange = Arrays.copyOfRange(data, i * 117, (i + 1) * 117);
				byte[] part = encryptCipher.doFinal(copyOfRange);
				for (byte b : part) {
					dist[distOffset++] = b;
				}
			}
			return dist; 
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public byte[] decrypt(byte[] data) {
		// 使用公钥解密
		try {
			int dataLen = data.length;
			int times = dataLen % PART_LEN == 0 ? dataLen / PART_LEN : dataLen / PART_LEN + 1;
			byte[] dist = new byte[117 * times];
			int distOffset = 0;
			for (int i = 0; i < times; i++) {
				byte[] copyOfRange = Arrays.copyOfRange(data, i * PART_LEN, (i + 1) * PART_LEN);
				byte[] part = decryptCipher.doFinal(copyOfRange);
				for (byte b : part) {
					dist[distOffset++] = b;
				}
			}
			dist = Arrays.copyOfRange(dist, 0, distOffset);
			return dist;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	public static void main(String[] args) {
		RSAEncipher encipher = new RSAEncipher();
		String src = "11111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111";
		System.out.println(src);
		byte[] encrypt = encipher.encrypt(src.getBytes());
		System.out.println(encrypt.length);
		System.out.println(new String(Base64.encodeBase64(encrypt)));
		System.out.println(new String(Base64.encodeBase64(encrypt)).length());
		byte[] decrypt = encipher.decrypt(encrypt);
		System.out.println(new String(decrypt));
	}

}
