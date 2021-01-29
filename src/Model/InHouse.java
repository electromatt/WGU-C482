package Model;

/**
 *
 * @author Matthew Druckhammer
 */
public class InHouse extends Part{

    private int machineId;

    /**
     * InHouse Part constructor.
     * @param id The id of the part.
     * @param name The name of the part.
     * @param price The price of the part.
     * @param stock The amount of part in stock.
     * @param min The minimum amount of part.
     * @param max The maximum amount of part.
     * @param machineId The machine ID of the of the in-house part.
     */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    /**
     * The getMachineID method gets the machine ID of the InHouse part.
     * @return Returns the machine ID of the InHouse part.
     */
    public int getMachineId() {
        return machineId;
    }

    /**
     * The setMachineId method sets the machine ID of the InHouse part.
     * @param machineId The machine ID of the InHouse part.
     */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }
}
