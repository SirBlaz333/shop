package com.my.dao.product.impl;

import com.my.dao.DBException;
import com.my.dao.DBManager;
import com.my.dao.product.ProductDAO;
import com.my.dao.product.sql.ProductSQLQueryBuilder;
import com.my.entity.CPU;
import com.my.entity.ProductFilterFormBean;
import com.my.entity.builder.CPUBuilder;
import com.my.entity.dto.CPUDataTransferObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProductDAOImpl implements ProductDAO {
    public static final String GET_PRODUCT_BY_ID = "SELECT * FROM products WHERE id = ?;";
    public static final String UPDATE_PRODUCT_AMOUNT = "UPDATE products SET amount = ? WHERE id = ?;";
    public static final String GET_PRODUCT_AMOUNT = "SELECT amount FROM products WHERE id = ?;";
    private final DBManager dbManager;
    private final Logger logger;

    public ProductDAOImpl(DBManager dbManager) {
        this.dbManager = dbManager;
        logger = Logger.getLogger(getClass().getName());
    }

    @Override
    public CPUDataTransferObject getProductById(int id) {
        CPUDataTransferObject cpuDataTransferObject = new CPUDataTransferObject();
        try (Connection connection = dbManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_PRODUCT_BY_ID);
            preparedStatement.setInt(BEGIN_INDEX, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                cpuDataTransferObject = buildCPU(resultSet);
            }
        } catch (SQLException | DBException e) {
            logger.log(Level.SEVERE, "Cannot get CPU by id");
        }
        return cpuDataTransferObject;
    }

    @Override
    public void updateProductAmount(CPU cpu, int amount) {
        try (Connection connection = dbManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_PRODUCT_AMOUNT);
            int index = BEGIN_INDEX;
            preparedStatement.setInt(index++, amount);
            preparedStatement.setInt(index, cpu.getId());
            preparedStatement.execute();
        } catch (SQLException | DBException e) {
            logger.log(Level.SEVERE, "Cannot update project amount");
        }
    }

    @Override
    public int getProductAmount(CPU cpu) {
        int amount = 0;
        try (Connection connection = dbManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_PRODUCT_AMOUNT);
            preparedStatement.setInt(BEGIN_INDEX, cpu.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                amount = resultSet.getInt(BEGIN_INDEX);
            }
        } catch (SQLException | DBException e) {
            logger.log(Level.SEVERE, "Cannot get project amount");
        }
        return amount;
    }

    @Override
    public List<CPUDataTransferObject> getProducts(ProductFilterFormBean bean, int pageSize, int pageCount) {
        List<CPUDataTransferObject> cpus = new ArrayList<>();
        ProductSQLQueryBuilder sqlQueryBuilder = new ProductSQLQueryBuilder();
        String query = sqlQueryBuilder.buildSelectQuery(bean, pageSize, pageCount);
        try (Connection connection = dbManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                cpus.add(buildCPU(resultSet));
            }
        } catch (SQLException | DBException e) {
            logger.log(Level.SEVERE, "Cannot get products");
        }
        return cpus;
    }

    @Override
    public int getProductCount(ProductFilterFormBean bean) {
        int amount = 0;
        ProductSQLQueryBuilder sqlQueryBuilder = new ProductSQLQueryBuilder();
        String query = sqlQueryBuilder.buildCountQuery(bean);
        try (Connection connection = dbManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                amount = resultSet.getInt(BEGIN_INDEX);
            }
        } catch (SQLException | DBException e) {
            logger.log(Level.SEVERE, "Cannot get count of products");
        }
        return amount;
    }

    private CPUDataTransferObject buildCPU(ResultSet resultSet) throws SQLException {
        int index = BEGIN_INDEX;
        CPUBuilder cpuBuilder = new CPUBuilder();
        int id = resultSet.getInt(index++);
        String name = resultSet.getString(index++);
        int manufacturerId = resultSet.getInt(index++);
        double price = resultSet.getDouble(index++);
        int coreNumber = resultSet.getInt(index++);
        int memoryTypeId = resultSet.getInt(index);
        CPU cpu = cpuBuilder.withId(id)
                .withName(name)
                .withPrice(price)
                .withCoreNumber(coreNumber)
                .buildCPU();
        CPUDataTransferObject cpuDataTransferObject = new CPUDataTransferObject();
        cpuDataTransferObject.setCpu(cpu);
        cpuDataTransferObject.setManufacturerId(manufacturerId);
        cpuDataTransferObject.setMemoryTypeId(memoryTypeId);
        return cpuDataTransferObject;
    }

}
