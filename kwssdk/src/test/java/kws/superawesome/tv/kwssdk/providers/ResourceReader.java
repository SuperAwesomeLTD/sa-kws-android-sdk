package kws.superawesome.tv.kwssdk.providers;

import org.jetbrains.annotations.NotNull;

import java.io.InputStream;
import java.util.Scanner;

/**
 * Created by guilherme.mota on 10/01/2018.
 */

public class ResourceReader {

    private static String kCHARSET = "UTF-8";
    private static String kDELIMITER = "\\A";

    static String readResource(@NotNull String name) {
        InputStream in = ClassLoader.getSystemClassLoader().getResourceAsStream(name);
        return convertStreamToString(in);
    }

    private static String convertStreamToString(InputStream is) {
        Scanner s = new Scanner(is, kCHARSET).useDelimiter(kDELIMITER);
        return s.hasNext() ? s.next() : "";
    }


}
