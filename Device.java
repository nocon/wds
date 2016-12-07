import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Device {
    public String brand;
    public String model;
    public String formFactor;
    public ArrayList<Attribute> attributes;

    public String getFullName() {
        return brand + " " + model;
    }

    public boolean isValid() {
        if (brand == null || brand.length() == 0 || brand.length() > 50) return false;
        if (model == null || model.length() == 0 || model.length() > 50) return false;
        List<String> factors = Arrays.asList(new String[] {"CANDYBAR", "SMARTPHONE", "PHABLET", "CLAMSHELL"});
        if (!factors.contains(formFactor)) return false;
        for (int i = 0; i < attributes.size(); i++) {
            if(attributes.get(i).name == null ||
                    attributes.get(i).name.length() == 0 ||
                    attributes.get(i).name.length() > 20) return false;
            if(attributes.get(i).value == null ||
                    attributes.get(i).value.length() == 0 ||
                    attributes.get(i).value.length() > 100) return false;
        }
        return true;
    }
}
