import static org.junit.Assert.assertEquals;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.util.ArrayList;
import java.nio.charset.StandardCharsets;

import org.junit.Test;

public class DeviceServiceTest {
    @Test
    public void givenEmptyFileLoadsNoDevices() {
        DeviceService deviceService = new DeviceService("[]");
        ArrayList<Device> devices = deviceService.list();
        assertEquals(0, devices.size());
        DeviceService deviceService2 = new DeviceService("");
        devices = deviceService2.list();
        assertEquals(0, devices.size());
    }

    @Test
    public void loadsAllDevices() {
        DeviceService deviceService = new DeviceService(loadDeviceFile());
        ArrayList<Device> devices = deviceService.list();
        assertEquals(3, devices.size());
    }

    @Test
    public void loadsFirstDeviceCorrectly() {
        DeviceService deviceService = new DeviceService(loadDeviceFile());
        ArrayList<Device> devices = deviceService.list();
        assertEquals("Mockia", devices.get(0).brand);
        assertEquals("5800", devices.get(0).model);
        assertEquals("CANDYBAR", devices.get(0).formFactor);
        assertEquals("Screen Size", devices.get(0).attributes.get(0).name);
        assertEquals("128mm", devices.get(0).attributes.get(0).value);
    }

    @Test
    public void givenExistingFullNameListsValidDevice() {
        DeviceService deviceService = new DeviceService(loadDeviceFile());
        ArrayList<Device> devices = deviceService.list("Phony X11");
        assertEquals(1, devices.size());
        assertEquals("Phony", devices.get(0).brand);
    }

    @Test
    public void givenNonExistingFullNameListsOnlyOneProduct() {
        DeviceService deviceService = new DeviceService(loadDeviceFile());
        ArrayList<Device> devices = deviceService.list("Not a phone");
        assertEquals(0, devices.size());
    }

    @Test
    public void givenOnlyModelListsValidProducts() {
        DeviceService deviceService = new DeviceService(loadDeviceFile());
        String model = "Phony";
        ArrayList<Device> devices = deviceService.list(model, null);
        assertEquals(true, devices.stream().allMatch(d -> d.model.equalsIgnoreCase(model)));
    }

    @Test
    public void givenOnlyBrandListsValidProducts() {
        DeviceService deviceService = new DeviceService(loadDeviceFile());
        String brand = "X11";
        ArrayList<Device> devices = deviceService.list(null, brand);
        assertEquals(true, devices.stream().allMatch(d -> d.brand.equalsIgnoreCase(brand)));
    }

    private String loadDeviceFile() {
        Path path = Paths.get("devices.json");
        String result;
        try {
            result = String.join("\n", Files.readAllLines(path, StandardCharsets.UTF_8));
            return result;
        } catch (Exception ex) {
            System.out.println("invalid file");
            return null;
        }
    }
}