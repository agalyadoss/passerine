from cimport import *
import java.util.Base64

def encryptMessage(plainText, pubKey):
    cipher = javax.crypto.Cipher.getInstance("RSA");
    cipher.init(javax.crypto.Cipher.ENCRYPT_MODE, pubKey)
    return java.util.Base64.getEncoder().encodeToString(cipher.doFinal(plainText))

def decryptMessage(encryptedText, privateKey):
    cipher = javax.crypto.Cipher.getInstance("RSA");
    cipher.init(javax.crypto.Cipher.DECRYPT_MODE, privateKey)
    return java.lang.String(cipher.doFinal(java.util.Base64.getDecoder().decode(encryptedText)))

def getKey():
    keyPairGenerator=java.security.KeyPairGenerator.getInstance("RSA");
    keyPairGenerator.initialize(1024, java.security.SecureRandom.getInstance("SHA1PRNG"))
    return keyPairGenerator.generateKeyPair()

keyPair=getKey()
encryptedText=encryptMessage("this is text", keyPair.getPrivate())
plainText=decryptMessage(encryptedText, keyPair.getPublic())

print(encryptedText)
print(plainText)
