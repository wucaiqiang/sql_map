import sun.misc.BASE64Encoder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.security.*;
import java.security.cert.Certificate;

public class SllKeyStore {


    private File keystoreFile;

    private String keyStoreType;

    private char[] password;


    private String alias;


    private File exportedFile;


    public static KeyPair getPrivateKey(KeyStore keystore, String alias, char[] password) {

        try {
            Key key = keystore.getKey(alias, password);

            if (key instanceof PrivateKey) {

                Certificate cert = keystore.getCertificate(alias);

                PublicKey publicKey = cert.getPublicKey();

                return new KeyPair(publicKey, (PrivateKey) key);

            }

        } catch (UnrecoverableKeyException e) {

        } catch (NoSuchAlgorithmException e) {

        } catch (KeyStoreException e) {

        }

        return null;

    }


    public void export() throws Exception {

        KeyStore keystore = KeyStore.getInstance(keyStoreType);

        BASE64Encoder encoder = new BASE64Encoder();

        keystore.load(new FileInputStream(keystoreFile), password);

        KeyPair keyPair = getPrivateKey(keystore, alias, password);

        PrivateKey privateKey = keyPair.getPrivate();

        String encoded = encoder.encode(privateKey.getEncoded());

        FileWriter fw = new FileWriter(exportedFile);

        fw.write("-----BEGIN RSA PRIVATE KEY-----\r\n");//私钥库文件必须以此开头，否则使用时会出错

        fw.write(encoded);

        fw.write("\r\n-----END RSA PRIVATE KEY-----");//私钥库文件必须以此结尾

        fw.close();


    }


    public static void main(String args[]) throws Exception {

        SllKeyStore export = new SllKeyStore();

        export.keystoreFile = new File("E:/software/ssl/test.keystore");//读取秘钥库keystore文件

        export.keyStoreType = KeyStore.getDefaultType();

        String passwordString = "123321"; //秘钥库口令

        export.password = passwordString.toCharArray();

        export.alias = "testalias";//秘钥库别名

        export.exportedFile = new File("E:/software/ssl/test.key");//生成的私钥文件

        export.export();

    }

}