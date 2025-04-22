package com.petplace.service;

import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class PetSizeClassificationService {
    private static final int SMALL_DOG_MAX_WEIGHT = 10;
    private static final int MEDIUM_DOG_MAX_WEIGHT = 25;

    public boolean matchesPetSizeCategory(String petSizeDesc, String targetSize) {
        if (petSizeDesc == null || petSizeDesc.isEmpty()) {
            return false;
        }

        petSizeDesc = petSizeDesc.toLowerCase();

        if (petSizeDesc.contains("모두") || petSizeDesc.contains("전체")) {
            return true;
        }

        switch (targetSize) {
            case "small":
                return petSizeDesc.contains("소형") || checkSmallDogWeight(petSizeDesc);
            case "medium":
                return petSizeDesc.contains("중형") || checkMediumDogWeight(petSizeDesc);
            case "large":
                return petSizeDesc.contains("대형") || checkLargeDogWeight(petSizeDesc);
            default:
                return false;
        }
    }

    public String[] getPetSizeCategories(String petSizeDesc) {
        if (petSizeDesc == null || petSizeDesc.isEmpty()) {
            return new String[0];
        }

        List<String> categories = new ArrayList<>();

        if (matchesPetSizeCategory(petSizeDesc, "small")) {
            categories.add("small");
        }
        if (matchesPetSizeCategory(petSizeDesc, "medium")) {
            categories.add("medium");
        }
        if (matchesPetSizeCategory(petSizeDesc, "large")) {
            categories.add("large");
        }

        return categories.toArray(new String[0]);
    }

    private boolean checkSmallDogWeight(String petSizeDesc) {
        if (petSizeDesc.contains("10kg") && (petSizeDesc.contains("미만") || petSizeDesc.contains("이하"))) {
            return true;
        }

        for (int i = 1; i < SMALL_DOG_MAX_WEIGHT; i++) {
            if (petSizeDesc.contains(i + "kg")) {
                return true;
            }
        }
        return false;
    }

    private boolean checkMediumDogWeight(String petSizeDesc) {
        if (petSizeDesc.contains("10kg") && (petSizeDesc.contains("25kg") || petSizeDesc.contains("이상") &&
                !petSizeDesc.contains("25kg") && !petSizeDesc.contains("30kg"))) {
            return true;
        }

        for (int i = SMALL_DOG_MAX_WEIGHT; i < MEDIUM_DOG_MAX_WEIGHT; i++) {
            if (petSizeDesc.contains(i + "kg")) {
                return true;
            }
        }
        return false;
    }

    private boolean checkLargeDogWeight(String petSizeDesc) {
        if (petSizeDesc.contains("25kg") && petSizeDesc.contains("이상")) {
            return true;
        }

        for (int i = MEDIUM_DOG_MAX_WEIGHT; i <= 100; i += (i < 40 ? 1 : 10)) {
            if (petSizeDesc.contains(i + "kg")) {
                return true;
            }
        }
        return false;
    }
}