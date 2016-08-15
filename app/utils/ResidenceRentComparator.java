package utils;

import java.util.Comparator;

import models.Message;
import models.Residence;

public class ResidenceRentComparator implements Comparator<Residence> {
	/**
	 * compare the rent attributes of each Residence use the Residence compare method
	 */

	@Override
	public int compare(Residence a, Residence b) {
		return Integer.compare((Integer.valueOf(a.rent)),(Integer.valueOf(b.rent)));

	}
}
