import java.util.UUID;

public class GenerateIdHelper {

    public static String generateId(String typeOfId){
        String uid = UUID.randomUUID().toString();
        switch (typeOfId){
            case "AP" :
               return "AP" + takeSubString(uid);
            default:
                return null;
        }
    }

    public static String takeSubString(String id){
        int pos = 0;
        for (int i = 0; i < 2; i++) {
            pos = id.indexOf("-", pos) +1;
        }
        return id.substring(0,pos-1);
    }
}
