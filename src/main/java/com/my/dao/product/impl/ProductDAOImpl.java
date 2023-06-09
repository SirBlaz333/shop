package com.my.dao.product.impl;

import com.my.dao.DBException;
import com.my.dao.DBManager;
import com.my.dao.product.ProductDAO;
import com.my.dao.product.sql.ProductSQLQueryBuilder;
import com.my.entity.cpu.Cpu;
import com.my.entity.order.OrderProduct;
import com.my.entity.ProductFilterFormBean;
import com.my.entity.cpu.CpuBuilder;
import com.my.entity.dto.CpuDTO;
import com.my.util.LoggerFactory;

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
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductDAOImpl.class);
    private final DBManager dbManager;

    public ProductDAOImpl(DBManager dbManager) {
        this.dbManager = dbManager;
    }

    @Override
    public CpuDTO getProductById(int id) {
        CpuDTO cpuDTO = new CpuDTO();
        try (Connection connection = dbManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_PRODUCT_BY_ID);
            preparedStatement.setInt(BEGIN_INDEX, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                cpuDTO = buildCPU(resultSet);
            }
        } catch (SQLException | DBException e) {
            LOGGER.log(Level.SEVERE, "Cannot get CPU by id");
        }
        return cpuDTO;
    }

    @Override
    public void buyProduct(List<OrderProduct> orderProducts) throws DBException {
        try (Connection connection = dbManager.getConnection()) {
            connection.setAutoCommit(false);
            for (OrderProduct orderProduct : orderProducts) {
                updateCpu(connection, orderProduct.getId(), orderProduct.getQuantity());
            }
            connection.commit();
        } catch (DBException e) {
            LOGGER.log(Level.SEVERE, "Cannot obtain a connection");
        } catch (SQLException e){
            throw new DBException("There is no such quantity in stock");
        }
    }

    private void updateCpu(Connection connection, int id, int amount) throws SQLException {
        amount = getProductAmount(id) - amount;
        PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_PRODUCT_AMOUNT);
        int index = BEGIN_INDEX;
        preparedStatement.setInt(index++, amount);
        preparedStatement.setInt(index, id);
        preparedStatement.execute();
    }

    private int getProductAmount(int id){
        Cpu cpu = new CpuBuilder()
                .withId(id)
                .buildCPU();
        return getProductAmount(cpu);
    }

    @Override
    public int getProductAmount(Cpu cpu) {
        int amount = 0;
        try (Connection connection = dbManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_PRODUCT_AMOUNT);
            preparedStatement.setInt(BEGIN_INDEX, cpu.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                amount = resultSet.getInt(BEGIN_INDEX);
            }
        } catch (SQLException | DBException e) {
            LOGGER.log(Level.SEVERE, "Cannot get project amount");
        }
        return amount;
    }

    @Override
    public List<CpuDTO> getProducts(ProductFilterFormBean bean) {
        List<CpuDTO> cpus = new ArrayList<>();
        ProductSQLQueryBuilder sqlQueryBuilder = new ProductSQLQueryBuilder();
        String query = sqlQueryBuilder.buildSelectQuery(bean);
        try (Connection connection = dbManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                cpus.add(buildCPU(resultSet));
            }
        } catch (SQLException | DBException e) {
            LOGGER.log(Level.SEVERE, "Cannot get products");
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
            if (resultSet.next()) {
                amount = resultSet.getInt(BEGIN_INDEX);
            }
        } catch (SQLException | DBException e) {
            LOGGER.log(Level.SEVERE, "Cannot get count of products");
        }
        return amount;
    }

    private CpuDTO buildCPU(ResultSet resultSet) throws SQLException {
        int index = BEGIN_INDEX;
        CpuBuilder cpuBuilder = new CpuBuilder();
        int id = resultSet.getInt(index++);
        String name = resultSet.getString(index++);
        int manufacturerId = resultSet.getInt(index++);
        double price = resultSet.getDouble(index++);
        int coreNumber = resultSet.getInt(index++);
        int memoryTypeId = resultSet.getInt(index);
        Cpu cpu = cpuBuilder.withId(id)
                .withName(name)
                .withPrice(price)
                .withCoreNumber(coreNumber)
                .buildCPU();
        CpuDTO cpuDTO = new CpuDTO();
        cpuDTO.setCpu(cpu);
        cpuDTO.setManufacturerId(manufacturerId);
        cpuDTO.setMemoryTypeId(memoryTypeId);
        return cpuDTO;
    }

}
