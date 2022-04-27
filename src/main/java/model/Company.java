package model;
import java.time.LocalDate;
import java.util.Date;

public class Company {
    private int id;
    private String companyName;
    private String  foundingDate;

    public Company() {
    }

    public Company(String companyName, String foundingDate,int id) {
        this.companyName = companyName;
        this.foundingDate = foundingDate;
        this.id=id;
    }
    public Company(String companyName, String foundingDate) {
        this.companyName = companyName;
        this.foundingDate = foundingDate;
    }


    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getFoundingDate() {
        return foundingDate;
    }

    public void setFoundingDate(String foundingDate) {
        this.foundingDate = foundingDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Company{" +
                "id=" + id +
                ", companyName='" + companyName + '\'' +
                ", foundingDate=" + foundingDate +
                '}';
    }
}
