package fr.superesi.superapp.monitoring;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import fr.superesi.superapp.Order;

/**
 * TableModel definition. Used in <code>MonitoringAppMainFrame</code> to display data table.
 * 
 * @author C. Esswein
 *
 */
@SuppressWarnings("serial")
public class OrdersTableModel extends AbstractTableModel {

	private String[] colNnames= {"Product","Unit price (euros)","Quantity","Total Price (euros)"};
	private ArrayList<Order> data;

	public OrdersTableModel(ArrayList<Order> data) {
		// storing data (i.e. list of Orders) in local variable
		this.data = data;
	}

	@Override
	public int getRowCount() {
		return data.size();
	}

	@Override
	public int getColumnCount() {
		return 4;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		switch (columnIndex) {

		case 0:
			return data.get(rowIndex).getProduct();
		case 1:
			return data.get(rowIndex).getUnitPrice();
		case 2:
			return data.get(rowIndex).getQuantity();
		case 3 : return (data.get(rowIndex).getUnitPrice()*
						 data.get(rowIndex).getQuantity());
		}
		return null;
	}
	@Override
	public String getColumnName(int col) {
        return colNnames[col];
    }

}
