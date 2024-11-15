package services.helper;

import java.util.UUID;

/**
 * This helper class provides static methods to generate IDs
 */
public class GenerateIdHelper {
    /**
     * @param typeOfId for the type of id to generate for
     * @return the ID generated
     */
    public static String generateId(String typeOfId){
        String uid = UUID.randomUUID().toString();
        switch (typeOfId){
            case "AP" :
               return "AP" + takeSubString(uid);
            case "RR":
                return "RR" + takeSubString(uid);
            case "TR":
                return "TR" + takeSubString(uid);
            default:
                return null;
        }
    }

    /**
     * @return part of the String
     */
    public static String takeSubString(String id){
        int pos = 0;
        for (int i = 0; i < 2; i++) {
            pos = id.indexOf("-", pos) +1;
        }
        return id.substring(0,pos-1);
    }
}
