package Model;

/**
 *
 * @author Matthew Druckhammer
 */
public class Outsourced extends Part {

    private String companyName;

    /**
     * Outsourced Part constructor.
     * @param id The id of the part.
     * @param name The name of the part.
     * @param price The price of the part.
     * @param stock The amount of part in stock.
     * @param min The minimum amount of part.
     * @param max The maximum amount of part.
     * @param companyName The company name of the outsourced part.
     */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /**
     * The getCompanyName method returns the company name of the Outsourced part.
     * @return Returns the name of the company.
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * The setCompanyName method sets the company name of the Outsourced part.
     * @param companyName The name of the company.
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
