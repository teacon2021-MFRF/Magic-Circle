package mfrf.magic_circle.knowledges;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import net.minecraft.nbt.CompoundNBT;

public class PlayerKnowledges {
    private int math;
    private int mystery;
    private int bagua;
    private int physical;
    private HashSet<String> unlockedResearchs = new HashSet<>();
    private HashSet<String> analysedItem = new HashSet<>();

    public PlayerKnowledges() {
        this.math = 0;
        this.mystery = 0;
        this.bagua = 0;
        this.physical = 0;
    }

    public PlayerKnowledges(int math, int mystery, int bagua, int physical, List<String> researches, List<String> items) {
        this.math = math;
        this.mystery = mystery;
        this.bagua = bagua;
        this.physical = physical;
        unlockedResearchs.addAll(researches);
        analysedItem.addAll(items);
    }

    public int getMath() {
        return math;
    }

    public int getBagua() {
        return bagua;
    }

    public int getMystery() {
        return mystery;
    }

    public int getPhysical() {
        return physical;
    }

    public void unlock(String name) {
        if (!unlockedResearchs.contains(name)) {
            unlockedResearchs.add(name);
        }
    }

    public boolean hasAllUnlocked(String[] name) {
        return unlockedResearchs.containsAll(Arrays.asList(name));
    }

    public boolean hasUnlocked(String name) {
        return unlockedResearchs.contains(name);
    }

    public void unlockItem(String name) {
        if (!analysedItem.contains(name)) {
            analysedItem.add(name);
        }
    }

    public boolean hasAllItemUnlocked(String[] name) {
        return analysedItem.containsAll(Arrays.asList(name));
    }

    public boolean hasItemUnlocked(String name) {
        return analysedItem.contains(name);
    }

    public void addMath(int math) {
        this.math += math;
    }

    public void addEightDiragrams(int eightDiragrams) {
        this.bagua += eightDiragrams;
    }

    public void addMystery(int mystery) {
        this.mystery += mystery;
    }

    public void addPhysical(int physical) {
        this.physical += physical;
    }

    public CompoundNBT serializeNBT() {
        CompoundNBT compoundNBT = new CompoundNBT();
        compoundNBT.putInt("math", math);
        compoundNBT.putInt("mystery", mystery);
        compoundNBT.putInt("bagua", bagua);
        compoundNBT.putInt("physical", physical);
        StringBuilder researches = new StringBuilder();
        StringBuilder items = new StringBuilder();
        for (String name : unlockedResearchs) {
            researches.append("|").append(name);
        }
        for (String name : analysedItem) {
            items.append("|").append(name);
        }
        compoundNBT.putString("researches", researches.toString());
        compoundNBT.putString("items", items.toString());
        return compoundNBT;
    }

    public static PlayerKnowledges deserializeNBT(CompoundNBT nbt) {
        int math = nbt.getInt("math");
        int mystery = nbt.getInt("mystery");
        int eightDiragrams = nbt.getInt("bagua");
        int physical = nbt.getInt("physical");
        List<String> researches = Arrays.stream(nbt.getString("researches").split("\\|")).collect(Collectors.toList());
        List<String> items = Arrays.stream(nbt.getString("items").split("\\|")).collect(Collectors.toList());
        return new PlayerKnowledges(math, mystery, eightDiragrams, physical, researches, items);
    }
}
