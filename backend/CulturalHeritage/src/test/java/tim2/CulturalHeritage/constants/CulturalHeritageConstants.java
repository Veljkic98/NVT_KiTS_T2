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
  public static final int NUMBER_OF_CH_IN_DB = 2;
  public static final double AVG_RATING_CH_ID_1 = 3.5;

  public static final Location LOCATION = new Location(LOCATION_ID, null, null, null, null, null);
  public static final CHSubtype CH_SUBTYPE = new CHSubtype(CH_SUBTYPE_ID, null, null);
  
}
