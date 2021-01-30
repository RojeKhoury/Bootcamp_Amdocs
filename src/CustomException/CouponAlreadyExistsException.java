package CustomException;

public class CouponAlreadyExistsException extends Exception{
	public CouponAlreadyExistsException(String errorMessage) {
		super("*Error* Coupon Already exist "+errorMessage);
	}
}
