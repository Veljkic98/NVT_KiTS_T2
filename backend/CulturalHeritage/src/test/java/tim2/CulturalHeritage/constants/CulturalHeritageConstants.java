package tim2.CulturalHeritage.constants;

import tim2.CulturalHeritage.model.CHSubtype;
import tim2.CulturalHeritage.model.Location;

public class CulturalHeritageConstants {
  public static final String NAME = "Muzej Nikole Tesle";
  public static final String DESCRIPTION = "Najlepsi muzej u Srbiji";
  public static final Long  LOCATION_ID = 1L;
  public static final Long  CH_SUBTYPE_ID = 1L;
  public static final Long CH_ID = 1L;
  public static final Long CH_ID_NOT_FOUND = 100L;
  public static final int PAGE_SIZE = 5;
  public static final int NUMBER_OF_CH_IN_DB = 3;

  public static final Location LOCATION = new Location(LOCATION_ID, null, null, null, null, null);
  public static final CHSubtype CH_SUBTYPE = new CHSubtype(CH_SUBTYPE_ID, null, null);

  public static final String SEARCH_NAME = "CH";
  public static final int SEARCH_NAME_RESULTS = 2;

  public static final String SEARCH_CITY = "Novi Sad";
  public static final int SEARCH_CITY_RESULTS = 1;

  public static final String SEARCH_SUBTYPE = "festival";
  public static final int SEARCH_SUBTYPE_RESULTS = 2;

  public static final String SEARCH_COUNTRY = "Kentucky";
  public static final int SEARCH_COUNTRY_RESULTS = 2;
}
