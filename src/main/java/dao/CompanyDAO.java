package dao;

import model.Company;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Set;

public interface CompanyDAO {
    Company getById(long id);

    Set<Company> getAll();

    Set<Company> get(int offset, int perPage, String sort);

    void save(Company company);

    void update(int id,Company company);

    void delete(long companyId);




}
