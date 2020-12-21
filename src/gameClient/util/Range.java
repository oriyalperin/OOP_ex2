package gameClient.util;
/**
 * This class represents a simple 1D range of shape [min,max]
 * @author boaz_benmoshe
 *
 */
public class Range {
	private double _min, _max;


	/**
	 * create a range from min to max
	 * */
	public Range(double min, double max) {
		set_min(min);
		set_max(max);
	}

	/**
	 * create a copy of range
	 * */
	public Range(Range x) {
		this(x._min, x._max);
	}


	/*public boolean isIn(double d) {
		boolean inSide = false;
		if(d>=this.get_min() && d<=this.get_max()) {inSide=true;}
		return inSide;
	}*/

	public String toString() {
		String ans = "["+this.get_min()+","+this.get_max()+"]";
		if(this.isEmpty()) {ans = "Empty Range";}
		return ans;
	}
	public boolean isEmpty() {
		return this.get_min()>this.get_max();
	}
	public double get_max() {
		return _max;
	}
	public double get_length() {
		return _max-_min;
	}
	
	private void set_max(double _max) {
		this._max = _max;
	}
	public double get_min() {
		return _min;
	}
	private void set_min(double _min) {
		this._min = _min;
	}

	/**
	 * divide the range length so each section will be in d length
	 * return the portion that represents every section
	 * */
	public double getPortion(double d) {
		double d1 = d-_min;
		double ans = d1/get_length();
		return ans;
	}

	/**
	 * multiple the range length in a portion
	 * return the x.max after the multiplication
	 * */
	public double fromPortion(double p) {
		return _min+p* get_length();
	}
}
