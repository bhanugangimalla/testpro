/*This class is used for writing keyword and logic for different scenarios in field level Validation*/
package graphQLFinal;

import org.testng.annotations.Test;

public class ConversionKeywords {
	static String convertedValue = null;
	@Test
	
	public String  conversionLogics(String actDestValue,String conversionKeyword) {
		
		//keyword to convert the datavalue of yes or no to true or false as expected---TRUE---yes
		if(conversionKeyword.equals("YesTrueNoFalse")) {
			if(actDestValue.equals("true")) {
				convertedValue = "Yes";
			}else if(actDestValue.equals("false")) {
				convertedValue = "No";
			}else {
				convertedValue = actDestValue;
			}
		}
		//keyword to convert the datavalue of yes or no to true or false as expected---100000000---false
		else if(conversionKeyword.equals("BinaryTrueFalse")) {
			if(actDestValue.equals("true")) {
				convertedValue = "100000000";
			}else if(actDestValue.equals("false")) {
				convertedValue = "100000001";
			}else {
				convertedValue = actDestValue;
			}
		}
		//for otherSpecialty[1]/primarySpecialtyFlag tags the source value is 100000003 and expected as false
		else if(conversionKeyword.equals("BinaryFalseEtc3")) {
			if(actDestValue=="false") {
				convertedValue = "100000003";
			}else {
				convertedValue = actDestValue;
			}
		}
		//for otherSpecialty[1]/primarySpecialtyFlag tags the source value is 100000005 and expected as false
		else if(conversionKeyword.equals("BinaryFalseEtc5")) {
			if(actDestValue=="false") {
				convertedValue = "100000005";
			}else {
				convertedValue = actDestValue;
			}
		}
		//for otherSpecialty[1]/primarySpecialtyFlag tags the source value is 100000005 and expected as false
		else if(conversionKeyword.equals("BinaryFalseEtc6")) {
					if(actDestValue=="false") {
						convertedValue = "100000006";
					}else {
						convertedValue = actDestValue;
					}
				}
		//for otherSpecialty[1]/primarySpecialtyFlag tags the source value is 100000007 and expected as false
		else if(conversionKeyword.equals("BinaryFalseEtc7")) {
					if(actDestValue=="false") {
						convertedValue = "100000007";
					}else {
						convertedValue = actDestValue;
					}
				}
		//for few tags in standardizedAddress when the source value is null "INVALID_REFERENCE_VALUE" is expected
		else if(conversionKeyword.equals("SchemaNullCode")) {
			if(actDestValue=="INVALID_REFERENCE_VALUE") {
				convertedValue = "null";
			}else {
				convertedValue = actDestValue;
			}
		}
		else if(conversionKeyword.equals("DatePattern1")) {
			
			//scenario 2000-07-01T00:00:00+00:00 ---- 2000-07-01T00:00:00
			if(actDestValue.contains("+")) {
			String str = actDestValue;
			
			convertedValue = str.subSequence(0,str.indexOf("+")).toString();
			}else {
				convertedValue = actDestValue;
			}
			
		}
		else if(conversionKeyword.equals("DatePattern2")) {
			
			//scenario 2015-10-22T00:00:00+00:00 ---- 20151022
			if(actDestValue.contains("+")) {
			String str1 = actDestValue;
			
			str1 = str1.subSequence(0,str1.indexOf("T")).toString();
			convertedValue = str1.replace("-", "");
			}
			else {
				convertedValue = actDestValue;
			}
			
		}
		else if(conversionKeyword.equals("DatePattern3")) {
			//scenario 2018-10-30T05:51:59+00:00 ---- 10/30/2018 5:51:59 AM
			
			if(actDestValue.contains("+")) {
			String str2 = actDestValue;
			int firstHifenIndex = nthOccurrence(str2, '-', 1);
			int secondHifenIndex = nthOccurrence(str2, '-', 2);
			int indexOfT = nthOccurrence(str2, 'T', 1);
			int firstColonIndex = nthOccurrence(str2, ':', 1);
			int secondColonIndex = nthOccurrence(str2, ':', 2);
			int indexOfPlus = nthOccurrence(str2, '+', 1);
			
			String month = str2.substring(firstHifenIndex+1, secondHifenIndex);
			String date = str2.substring(secondHifenIndex+1, indexOfT);
			String year = str2.substring(0, firstHifenIndex);
			String hour = str2.substring(indexOfT+1, firstColonIndex);
			String minutes = str2.substring(firstColonIndex+1, secondColonIndex);
			String seconds = str2.substring(secondColonIndex+1, indexOfPlus);
			String ext = "AM";
			
			if(hour.startsWith("0")) 
				hour = hour.replaceAll("0", "");
			
			if(month.startsWith("0")) 
				month = month.replaceAll("0", "");
			
			if(date.startsWith("0")) 
				date = date.replaceAll("0", "");
			
			if(Integer.parseInt(hour) >=12)
				ext = "PM";
			
			convertedValue = month + "/" + date + "/" + year + " " + hour + ":" + minutes + ":" + seconds + " " + ext;
			}
			else {
				convertedValue = actDestValue;
			}
				
		}else if(conversionKeyword.equals("CountryFormat")) {
			if(actDestValue.equals("UNITED_STATES")) {
				convertedValue = "United States";
			}else {
				convertedValue = actDestValue;
			}
		}
		//First character of the String remain as Caps and rest as small
		else if(conversionKeyword.equals("IgnoreCase")) {
				String firstCharacter = actDestValue.substring(0,1);
				String ignoreCaseString = firstCharacter + actDestValue.substring(1, actDestValue.length()).toLowerCase();
				convertedValue = ignoreCaseString;
		}
		//In the country name 1st char and 1st char post Underscore remains caps, undersocre replaced with whitespace
				else if(conversionKeyword.equals("CountryCode")) {
					if(actDestValue.equals("UNITED_STATES")) {
						convertedValue = "United States";
						
					}if(actDestValue.equals("UNITED_KINGDOM")) {
						convertedValue = "United Kingdom";
						
					}
					else if(!actDestValue.contains("_")) {
						String firstCharacter = actDestValue.substring(0,1);
						String ignoreCaseString = firstCharacter + actDestValue.substring(1, actDestValue.length()).toLowerCase();
						convertedValue = ignoreCaseString;
					}
				}
		
				else if(conversionKeyword.equals("GenderCode")) {
					if(actDestValue.equals("MALE_ONLY")) {
						convertedValue = "Male Only";
						
					}if(actDestValue.equals("FEMALE_ONLY")) {
						convertedValue = "Female Only";
						
					}
					else if(!actDestValue.contains("_")) {
						String firstCharacter = actDestValue.substring(0,1);
						String ignoreCaseString = firstCharacter + actDestValue.substring(1, actDestValue.length()).toLowerCase();
						convertedValue = ignoreCaseString;
					}
				}
				else if(conversionKeyword.equals("PracticeCode")) {
					
					//scenario 2015-10-22T00:00:00+00:00 ---- 20151022
					if(actDestValue.equals("INPATIENT_OUTPATIENT_OR_OUTPATIENT_ONLY")) {
					convertedValue = "Inpatient/Outpatient or Outpatient Only";
					}
					else {
						convertedValue = actDestValue;
					}
					
				}
		else {
			convertedValue = "No such Keyword exists,Please check and place new keyword";
		}
		return convertedValue;
	}
	
	public static int nthOccurrence(String s, char c, int occurrence) {
	    return nthOccurrence(s, 0, c, 0, occurrence);
	}

	public static int nthOccurrence(String s, int from, char c, int curr, int expected) {
	    final int index = s.indexOf(c, from);
	    if(index == -1) return -1;
	    return (curr + 1 == expected) ? index : 
	        nthOccurrence(s, index + 1, c, curr + 1, expected);
	}
	
}
