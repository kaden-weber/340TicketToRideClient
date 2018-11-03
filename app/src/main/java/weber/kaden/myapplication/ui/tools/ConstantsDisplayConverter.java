package weber.kaden.myapplication.ui.tools;

import weber.kaden.common.model.City;

public class ConstantsDisplayConverter {

    public ConstantsDisplayConverter(){

    }

    public String displayCity(City city){
        switch (city){
            case MIAMI:
                return ("Miami");
            case OMAHA:
                return ("Omaha");
            case BOSTON:
                return ("Boston");
            case DALLAS:
                return ("Dallas");
            case DENVER:
                return ("Denver");
            case DULUTH:
                return ("Duluth");
            case HELENA:
                return ("Helena");
            case ATLANTA:
                return ("Atlanta");
            case CALGARY:
                return ("Calgary");
            case CHICAGO:
                return ("Chicago");
            case EL_PASO:
                return ("El Paso");
            case HOUSTON:
                return ("Houston");
            case PHOENIX:
                return ("Phoenix");
            case RALEIGH:
                return ("Raleigh");
            case SEATTLE:
                return ("Seattle");
            case TORONTO:
                return ("Toronto");
            case MONTREAL:
                return ("Montreal");
            case NEW_YORK:
                return ("New York");
            case PORTLAND:
                return ("Portland");
            case SANTA_FE:
                return ("Santa Fe");
            case WINNIPEG:
                return ("Winnipeg");
            case LAS_VEGAS:
                return ("Las Vegas");
            case NASHVILLE:
                return ("Nashville");
            case VANCOUVER:
                return ("Vancouver");
            case CHARLESTON:
                return ("Charleston");
            case PITTSBURGH:
                return ("Pittsburgh");
            case WASHINGTON:
                return ("Washington");
            case KANSAS_CITY:
                return ("Kansas City");
            case LITTLE_ROCK:
                return ("Little Rock");
            case LOS_ANGELES:
                return ("Los Angeles");
            case NEW_ORLEANS:
                return ("New Orleans");
            case OKLAHOMA_CITY:
                return ("Oklahoma City");
            case SAN_FRANCISCO:
                return ("San Francisco");
            case SALT_LAKE_CITY:
                return ("Salt Lake City");
            case SAULT_ST_MARIE:
                return ("Sault St. Marie");
            case SAINT_LOUIS:
                return ("Saint Louis");
            default:
                return "unknown";
        }
    }


}
