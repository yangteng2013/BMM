package app.banking.bankmuscat.merchant.util;


import com.mahindracomviva.common.database.CardDetail;

/**
 * https://github.com/jgrana/CreditCardEntry/blob/master/CreditCardEntry/src/com
 * /devmarvel/creditcardentry/internal/CreditCardUtil.java
 * 
 * @author u36838
 * 
 */
public class CardUtil {
	// See: http://www.regular-expressions.info/creditcard.html
	public static final String REGX_VISA = "^4[0-9]{15}?";// VISA 16
	public static final String REGX_MC = "^5[1-5][0-9]{14}$"; // MC 16
	public static final String REGX_AMEX = "^3[47][0-9]{13}$";// AMEX 15
	public static final String REGX_DISCOVER = "^6(?:011|5[0-9]{2})[0-9]{12}$";// Discover
																				// 16
	public static final String REGX_DINERS_CLUB = "^3(?:0[0-5]|[68][0-9])[0-9]{11}$";// DinersClub
																						// 14
																						// 38812345678901

	public static final int CC_LEN_FOR_TYPE = 4; // number of characters to
													// determine length

	public static final String REGX_AMEX_REG_TYPE = "^3[47][0-9]{2}$";// AMEX 15
	public static final String REGX_DINERS_CLUB_TYPE = "^3(?:0[0-5]|[68][0-9])[0-9]$";// DinersClub
	public static final String REGX_VISA_TYPE = "^4[0-9]{3}?";// VISA 16
	public static final String REGX_MC_TYPE = "^5[1-5][0-9]{2}$";// MC 16
	public static final String REGX_DISCOVER_TYPE = "^6(?:011|5[0-9]{2})$";// Discover
	private static final String TAG ="TAG" ;
	private static CardDetail[] sdkcards;
	private static int totalcards;
	// 16

	/**
	 * clean card number
	 * 
	 * @param cardNumber
	 * @return
	 */
	public static String cleanNumber(String cardNumber) {
		return cardNumber.replaceAll("\\s", "");
	}

	/**
	 * find card type
	 * 
	 * @param cardNumber
	 * @return
	 */
	/*public static CardType findCardType(String cardNumber) {

		if (cardNumber.length() < CC_LEN_FOR_TYPE) {
			return CardType.INVALID;

		}

		String reg = null;

		for (CardType type : CardType.values()) {
			switch (type) {
			case AMEX:
				reg = REGX_AMEX_REG_TYPE;
				break;
			case DISCOVER:
				reg = REGX_DISCOVER_TYPE;
				break;
			case MASTER:
				reg = REGX_MC_TYPE;
				break;
			case VISA:
				reg = REGX_VISA_TYPE;
				break;
			default:
				break;
			}

			Pattern pattern = Pattern.compile(reg);
			Matcher matcher = pattern.matcher(cardNumber
					.substring(0, CC_LEN_FOR_TYPE));

			if (matcher.matches()) {
				return type;
			}
		}

		return CardType.INVALID;
	}

	*//**
	 * check if card number is valid
	 * 
	 * @param cardNumber
	 * @return
	 *//*
	public static boolean isValidNumber(String cardNumber) {
		boolean result = false;

		String cleaned = cleanNumber(cardNumber);

		String reg = null;

		switch (findCardType(cleaned)) {
		case AMEX:
			reg = REGX_AMEX;
			break;
		case DISCOVER:
			reg = REGX_DISCOVER;
			break;
		case INVALID:
			return result;
		case MASTER:
			reg = REGX_MC;
			break;
		case VISA:
			reg = REGX_VISA;
			break;
		default:
			return result;
		}

		Pattern pattern = Pattern.compile(reg);
		Matcher matcher = pattern.matcher(cleaned);

		return matcher.matches() && validateCardNumber(cleaned);
	}

	*//**
	 * validate card number
	 * 
	 * @param cardNumber
	 * @return
	 * @throws NumberFormatException
	 *//*
	public static boolean validateCardNumber(String cardNumber) throws NumberFormatException {
		int sum = 0, digit, addend = 0;
		boolean doubled = false;
		for (int i = cardNumber.length() - 1; i >= 0; i--) {
			digit = Integer.parseInt(cardNumber.substring(i, i + 1));
			if (doubled) {
				addend = digit * 2;
				if (addend > 9) {
					addend -= 9;
				}
			} else {
				addend = digit;
			}
			sum += addend;
			doubled = !doubled;
		}
		return (sum % 10) == 0;
	}

	*//**
	 * fromat any card number for viewing
	 * 
	 * @param enteredNumber
	 * @return
	 *//*
	public static String formatForViewing(String enteredNumber) {
		return formatForViewing(enteredNumber, findCardType(enteredNumber));
	}

	*//**
	 * format card number from viewing
	 * 
	 * @param enteredNumber
	 * @param type
	 * @return
	 *//*
	public static String formatForViewing(String enteredNumber, CardType type) {
		String cleaned = cleanNumber(enteredNumber);
		int len = cleaned.length();

		if (len <= CC_LEN_FOR_TYPE)
			return cleaned;


		ArrayList<String> gaps = new ArrayList<String>();

		int segmentLengths[] = { 0, 0, 0 };

		switch (type) {
		case VISA:
		case MASTER:
		case DISCOVER: // { 4-4-4-4}
			gaps.add(" ");
			segmentLengths[0] = 4;
			gaps.add(" ");
			segmentLengths[1] = 4;
			gaps.add(" ");
			segmentLengths[2] = 4;
			break;
		case AMEX: // {4-6-5}
			gaps.add(" ");
			segmentLengths[0] = 6;
			gaps.add(" ");
			segmentLengths[1] = 5;
			gaps.add("");
			segmentLengths[2] = 0;
			break;
		default:
			return enteredNumber;
		}

		int end = CC_LEN_FOR_TYPE;
		int start = 0;
		String segment1 = cleaned.substring(0, end);
		start = end;
		end = segmentLengths[0] + end > len ? len : segmentLengths[0] + end;
		String segment2 = cleaned.substring(start, end);
		start = end;
		end = segmentLengths[1] + end > len ? len : segmentLengths[1] + end;
		String segment3 = cleaned.substring(start, end);
		start = end;
		end = segmentLengths[2] + end > len ? len : segmentLengths[2] + end;
		String segment4 = cleaned.substring(start, end);

		String ret = String
				.format("%s%s%s%s%s%s%s", segment1, gaps.get(0), segment2, gaps
						.get(1), segment3, gaps.get(2), segment4);

		return ret.trim();
	}

	public static int lengthOfStringForType(CardType type) {
		int idx = 0;

		switch (type) {
		case VISA:
		case MASTER:
		case DISCOVER: // { 4-4-4-4}
			idx = 16;
			break;
		case AMEX: // {4-6-5}
			idx = 15;
			break;
		default:
			idx = 0;
		}

		return idx;
	}

	*//**
	 * card number length
	 * 
	 * @param type
	 * @return
	 *//*
	public static int lengthOfFormattedStringForType(CardType type) {
		int idx = 0;

		switch (type) {
		case VISA:
		case MASTER:
		case DISCOVER: // { 4-4-4-4}
			idx = 16 + 3;
			break;
		case AMEX: // {4-6-5}
			idx = 15 + 2;
			break;
		default:
			idx = 0;
		}

		return idx;
	}

	public static int lengthOfFormattedStringTilLastGroupForType(CardType type) {
		int idx = 0;

		switch (type) {
		case VISA:
		case MASTER:
		case DISCOVER:
			*//** { 4-4-4-4} *//*
			idx = 16 + 3 - 4;
			break;
		case AMEX:
			*//** {4-6-5} *//*
			idx = 15 + 2 - 5;
			break;
		default:
			idx = 0;
		}
		return idx;
	}

	*//**
	 * formate expire date
	 * 
	 * @param text
	 * @return
	 *//*
	public static String formatExpirationDate(String text) {

		try {
			switch (text.length()) {
			case 1:
				int digit = Integer.parseInt(text);

				if (digit < 2) {
					return text;
				} else {
					return "0" + text + "/";
				}
			case 2:
				int month = Integer.parseInt(text);
				if (month > 12 || month < 1) {
					*//** Invalid digit *//*
					return text.substring(0, 1);
				} else {
					return text + "/";
				}
			case 3:
				if (text.substring(2, 3).equalsIgnoreCase("/")) {
					return text;
				} else {
					text = text.substring(0, 2) + "/" + text.substring(2, 3);
				}
			case 4:
				int yearDigit = Integer.parseInt(text.substring(3, 4));
				String year = String.valueOf(Calendar.getInstance()
						.get(Calendar.YEAR));
				int currentYearDigit = Integer.parseInt(year.substring(2, 3));
				if (yearDigit < currentYearDigit) {
					*//** Less than current year invalid *//*
					return text.substring(0, 3);
				} else {
					return text;
				}
			case 5:
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/yy");
				simpleDateFormat.setLenient(false);
				Date expiry = simpleDateFormat.parse(text);
				if (expiry.before(new Date())) {
					return text.substring(0, 4);
				} else {
					return text;
				}
			default:
				if (text.length() > 5) {
					return text.substring(0, 5);
				} else {
					return text;
				}
			}

		} catch (ParseException e) {
			*//** *//*
		}
		*//** If an exception is thrown we clear out the text *//*
		return "";
	}

	*//**
	 * get security code valid
	 * 
	 * @param type
	 * @return
	 *//*
	public static int securityCodeValid(CardType type) {
		switch (type) {
		case AMEX:
			return 4;
		case DISCOVER:
		case INVALID:
		case MASTER:
		case VISA:
		default:
			return 3;
		}
	}

	public static ArrayList<Card> createCardList(SoapObject response) {
		ArrayList<Card> result = new ArrayList<Card>();
		Card card;
		for (int i = 0; i < response.getPropertyCount(); i++) {
			Object property = response.getProperty(i);
			if (property instanceof SoapObject) {
				card = Card.create((SoapObject) property);
				if (null != card)
					result.add(card);
			}
		}
		return result;
	}
	
	public static ArrayList<Card> createJsonCardList(JSONArray cardJsonArray, Context context) {
		ArrayList<Card> result = new ArrayList<Card>();
		Card card;
		for (int i = 0; i < cardJsonArray.size(); i++)
		{
			card = Card.JsonNcreate((JSONObject)cardJsonArray.get(i));
			if (null != card)
				result.add(card);
		}
		return result;
	}
	

	
	
	
	public static ArrayList<Card> createJsonHCECardList(JSONArray cardJsonArray, Context context) {
		ArrayList<Card> result = new ArrayList<Card>();


		try
		{
			final CardProfileHandler cardHandler = new CardProfileHandler(context);
			sdkcards = cardHandler.getCardList();
			totalcards = cardHandler.getCardCount();

			CardDetail cardDetailObject = new CardDetail();
			int j = 0;
			for (; j < totalcards; j++) {
				Card card = new Card();
				cardDetailObject = sdkcards[j];
				cardDetailObject.getCardState();
				cardDetailObject.getCardStatus();
				cardDetailObject.getCardType();



				card.setHceId(cardDetailObject.getCardID().toString());
				card.setCardNumber(cardDetailObject.getCardID().toString().substring(0, 16));
				card.setInstrumentType(cardDetailObject.getCardType().toString());
				//card.setHceId(cardDetailObject.getCardID().toString());
				card.setCardCategory("HCE");
				result.add(card);
			}
		}catch (Exception e)
		{
			Log.d(TAG, "createJsonHCECardList: ");
		}
















		*//*for (int i = 0; i < cardJsonArray.size(); i++)
		{
			JSONObject getcardListObj1 =(JSONObject)cardJsonArray.get(i);
			if(null== (JSONArray) getcardListObj1.get("cardServiceMatrixList")){
				continue;
			}
			JSONArray cardsermat = (JSONArray) getcardListObj1.get("cardServiceMatrixList");
			if(cardsermat.size()==0)
				continue;
			
			JSONObject  getdetails =(JSONObject)cardJsonArray.get(i);
		           if( getdetails.containsKey("cardServiceMatrixList"))
		           {
		           card = Card.Jsoncreate((JSONObject)cardJsonArray.get(i));

					   final CardProfileHandler cardHandler = new CardProfileHandler(context);
					   sdkcards = cardHandler.getCardList();
					   totalcards = cardHandler.getCardCount();

					   CardDetail cardDetailObject = new CardDetail();
					   int j = 0;
					   for (; j < totalcards; j++) {
						   cardDetailObject = sdkcards[j];

						   String cardId = cardDetailObject.getCardID();


						   if(cardId.equals(card.getHceId()) && null != card)
						   {
							   result.add(card);
							   break;
						   }
					   }


		           }
		}*//*
		return result;
	}

	public static ArrayList<Bank> createBankList(JSONArray bankJsonArray) {
		ArrayList<Bank> result = new ArrayList<Bank>();
		Bank bank;
		for (int i = 0; i < bankJsonArray.size(); i++)
		{
			       bank = Bank.JsonNcreate((JSONObject)bankJsonArray.get(i));
		           if (null != bank)
					result.add(bank);
		}
		return result;
	}

	public static ArrayList<Address> createAddressList(SoapObject response) {
		ArrayList<Address> result = new ArrayList<Address>();
		Address addr;
		for (int i = 0; i < response.getPropertyCount(); i++) {
			Object property = response.getProperty(i);
			if (property instanceof SoapObject) {
				addr = Address.create((SoapObject) property);
				if (null != addr)
					addr.setUserName(response.getPropertyAsString(3));
				result.add(addr);
			}
		}
		return result;
	}

	public static ArrayList<Movies> createMoviesList(SoapObject response) {
		ArrayList<Movies> result = new ArrayList<Movies>();
		Movies addr;
		for (int i = 0; i < response.getPropertyCount(); i++) {
			Object property = response.getProperty(i);
			if (property instanceof SoapObject) {
				CLog.i("Util", property + "");
				addr = Movies.create((SoapObject) property);
				result.add(addr);
			}
		}
		return result;
	}

	public static ArrayList<Cinemas> createTheatresList(SoapObject response) {
		ArrayList<Cinemas> result = new ArrayList<Cinemas>();
		Cinemas addr;
		for (int i = 0; i < response.getPropertyCount(); i++) {
			Object property = response.getProperty(i);
			if (property instanceof SoapObject) {
				CLog.i("UtilValue:", ((SoapObject) property)
						.getPropertyAsString("showTimings"));
				addr = Cinemas.create((SoapObject) property);
				result.add(addr);
			}
		}
		return result;
	}

	public static ArrayList<CouponPreferances> createCouponPreferances(SoapObject response) {
		ArrayList<CouponPreferances> result = new ArrayList<CouponPreferances>();
		CouponPreferances addr;
		for (int i = 0; i < response.getPropertyCount(); i++) {
			Object property = response.getProperty(i);
			if (property instanceof SoapObject) {
				CLog.i("UtilValue:", ((SoapObject) property)
						.getPropertyAsString("preferenceType") + "---" + ((SoapObject) property)
						.getPropertyAsString("preferenceName"));
				addr = CouponPreferances.create((SoapObject) property);
				result.add(addr);
			}
		}
		return result;
	}

	public static ArrayList<OfferList> createOffersList(SoapObject response) {
		ArrayList<OfferList> result = new ArrayList<OfferList>();
		OfferList addr;
		for (int i = 0; i < response.getPropertyCount(); i++) {
			Object property = response.getProperty(i);
			if (property instanceof SoapObject) {
				addr = OfferList.create((SoapObject) property);
				result.add(addr);
			}
		}
		return result;
	}

	public static ArrayList<TransactionHistoryItems> createTransactionHistory(SoapObject response) {
		ArrayList<TransactionHistoryItems> result = new ArrayList<TransactionHistoryItems>();
		TransactionHistoryItems addr;
		for (int i = 0; i < response.getPropertyCount(); i++) {
			Object property = response.getProperty(i);
			if (property instanceof SoapObject) {
				CLog.i("UtilValue:", ((SoapObject) property)
						.getPropertyAsString("txnId") + "---" + ((SoapObject) property)
						.getPropertyAsString("merchantName"));
				addr = TransactionHistoryItems.create((SoapObject) property);
				result.add(addr);
			}
		}
		return result;
	}*/
}
