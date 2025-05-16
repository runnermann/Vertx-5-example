package com.runnermann.wolf.example.utility;

import java.util.HashMap;

public final class BusAddressMap {
    private static final HashMap<String, String> BUS_ADDRESS = new HashMap<>();


    public static String getAddress(String address) {
        if (BUS_ADDRESS.containsKey(address)) {
            return BUS_ADDRESS.get(address);
        }

        throw new IllegalArgumentException("No bus address found for address: " + address);
    }

    public static void putAddress(String address, String busAddress) {
        if(BUS_ADDRESS.containsKey(address)) {
            throw new IllegalArgumentException("Duplicate bus address: " + address + "Address already exists. Use get or" +
                    "try a different one.");
        }
        BUS_ADDRESS.put(address, busAddress);
    }
    
}
