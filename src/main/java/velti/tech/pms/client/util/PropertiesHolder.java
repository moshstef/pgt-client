package velti.tech.pms.client.util;


import java.util.Map;

public class PropertiesHolder {
    private Map<String,String> propertiesMap;

    public Map<String, String> getPropertiesMap() {
        return propertiesMap;
    }

    public void setPropertiesMap(Map<String, String> propertiesMap) {
        this.propertiesMap = propertiesMap;
    }
    
    public String getStringValue(String key) {
        return propertiesMap.get(key);
    }

    public Long getLongValue(String key) {
        return Long.parseLong(propertiesMap.get(key));
    }
}
