import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;



public class DeviceService {
    private String devices;

    public DeviceService(String devices) {
        this.devices = devices;
    }

    public ArrayList<Device> list() {
        if (devices == "") return new ArrayList<Device>();
        Gson gson = new Gson();
        Type listType = new TypeToken<ArrayList<Device>>(){}.getType();
        ArrayList<Device> result = gson.fromJson(devices, listType);
        return result;
    }

    public ArrayList<Device> list(String fullName) {
        return list()
                .stream()
                .filter(d -> d.getFullName().equalsIgnoreCase(fullName))
                .collect(Collectors.toCollection(ArrayList::new));

    }

    public ArrayList<Device> listValid(String fullName) {
        ArrayList<Device> result = list();
        result.stream().filter(d -> !d.isValid()).forEach(d -> System.out.println(d));
        return list()
                .stream()
                .filter(d -> d.isValid())
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public ArrayList<Device> list(String model, String brand) {
        System.out.print(list()
                .stream()
                .filter(d ->
                        (model == null || d.model.equalsIgnoreCase(model)) &&
                        (brand == null || d.brand.equalsIgnoreCase(brand))).count());
        return list()
                .stream()
                .filter(d ->
                        (model == null || d.model.equalsIgnoreCase(model)) &&
                        (brand == null || d.brand.equalsIgnoreCase(brand)))
                .collect(Collectors.toCollection(ArrayList::new));
    }
}