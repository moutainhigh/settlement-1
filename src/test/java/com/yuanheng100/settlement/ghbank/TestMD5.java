package com.yuanheng100.settlement.ghbank;

import com.yuanheng100.settlement.ghbank.utils.GhbankRSA;
import org.junit.Test;

/**
 * Created by jlqian on 2017/4/17.
 */
public class TestMD5
{
    private String source = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><Document><header><encryptData></encryptData><serverFlow>OGW01201603161000008948</serverFlow><status>0</status><serverTime>175419</serverTime><errorCode>0</errorCode><errorMsg></errorMsg><serverDate>20160316</serverDate></header><body><TRANSCODE>OGW00019</TRANSCODE><XMLPARA>NVnwXP2rp6vOBT0MUM7ksYWuVU9JH/yK6Emtf8BLHTgMyyBjG2uy0nEzEJ40ch0dk0Q0bjvGw8/EFp7ccofRz07f5/gYJZq4Dwj7d+X8vyg2hnjyLfTJDCoDsBZ26ypULjYyPO5EjOLEUPTVbwX3/4L49nXtIStjYk6RgCDoV6LUjr3lJgOt+EkETdLRiYb0</XMLPARA></body></Document>";

    private String target = "0F4D09076E73B16CFB29EA8F7729F34BC3AAA578AB3732AAA49BA8C9121513BA6B5F37B30F26E0DE96F905237FC05F303D58D3FDE28DC3FF273E0CE72504C37F4FE49465C76331DB27E8B223E901CC6B79722AE909BFD2922FCCC90D57C372F8BA14260FAEBC07577BDF92518A35F27D7786960B8B41459322027D913FF9971C";

    public static String privateString = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAKfLOLGj8Uo9jV7RXbJwUH70wyS9I/9Hb7Hhmy8gntiCa+7YGkcQdC3MxataPjStmx/txZa6TOHK0cUP4MjJGOvuQVsaD3e/i3RZ9zH/sWrdIi9TentT+erCRXxe7QVJy9JkkTcwsjyK8fBf3yexjBRd3mzkZgwFoSBnu/aJklT5AgMBAAECgYBJS8iUMJ0yZPjgNmzLiaxgCONFpSmYVchA1+BGEeXRhgdH6jZwXIujhhPC6kTKFhvxMphhYzg6WbKQdoMmslGj6Lf0T8MQhIWdveanrtK+Stv/USGNhZJ4W8YgECH47Y6cPrdAhVxAoU033gFfF9zQKTRRG9fzPYmBs3MxnyKOEQJBANqULCSQqONddl19PNPedH2Xq2Xio3qBOIZqe2vXpSslIqmOVEh5e6XNqkGyJ5tzgc1hgXToj0yH08yvKwxodN8CQQDEhUF9bH3h4jZ4V20gtsQsunZcipW4WWqs5k7n+hL4RmE0jzc1p7ESOufEBsDV2vpQEDtzIRFdzygTp9g82VknAkBPzR8crm/qqrnHQi0OET6oh9I83XTgCgOQF5o98jpMOT+hdeRRnYDGNQM+/wM03wP57upru2huAX0TUrS9z/kzAkEArtNVJX0kp5SUnI46GMY+0wO2VDE9bFivm8zvGVPRGGmUBCv8E5Fw5yTcFflkB1vdHuix2oPqbSfjIUlKByfcNQJBAI1em4L50QBRX2W3tp1+s/tVM/j9+IDiSOJSDSXfCYq8H+uoEd9yJYwuZLb2nlO4H79rvUkjwlHVcYPdHt/XT1g=";

    public static String publicString = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnyzixo/FKPY1e0V2ycFB+9MMkvSP/R2+x4ZsvIJ7Ygmvu2BpHEHQtzMWrWj40rZsf7cWWukzhytHFD+DIyRjr7kFbGg93v4t0Wfcx/7Fq3SIvU3p7U/nqwkV8Xu0FScvSZJE3MLI8ivHwX98nsYwUXd5s5GYMBaEgZ7v2iZJU+QIDAQAB";

    private Integer target_length = 256;

    @Test
    public void test() throws Exception
    {

        String md5Result = GhbankRSA.MD5(source);

        System.out.println(md5Result);

        byte[] bytes = GhbankRSA.RSAEncode(GhbankRSA.restorePrivateKey(GhbankRSA.decryptBASE64(privateString)), md5Result.getBytes("UTF-8"));

        String s = GhbankRSA.byteArrayToHexString(bytes);

        System.out.println(s.length());

        s = "BB7D0A571BAA4C9BCBA42F57B8B72B42487D5D7FD87B2E1021EFFB9C76A00211828E5104FBE69E288A6ABFFF34DFB82EDE001F9AF4E3A7EB8F7C89768CA45BAA20DF9B3A9E95CCBC541B6D548887A223CACAD559E675DC4491F5C3F349B8BB7F1257686054D64618D75BBAEA2D9D48A4D70E73D3FEF4DB7106284D889A1DF406";

        String s1 = GhbankRSA.RSADecode(GhbankRSA.restorePublicKey(GhbankRSA.decryptBASE64(publicString)), GhbankRSA.hexStringToByte(s));

        System.out.println(s1);


    }
}
