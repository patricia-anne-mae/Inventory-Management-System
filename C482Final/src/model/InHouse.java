package model;

/** Extension of part model */
public class InHouse extends Part {
    private int machineId;

    /** Constructors are used to initialize an object. Below is the constructor for InHouse*/
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    /** Accessor (getter) */
    public int getMachineId() {
        return machineId;
    }

    /** Mutator (setter) */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }
}

