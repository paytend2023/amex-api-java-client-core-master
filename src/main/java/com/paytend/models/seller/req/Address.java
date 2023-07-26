package com.paytend.models.seller.req;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

/**
 * object REQUIRED
 * Details on the Significant Owner's residential address.
 *
 * @author gudongyang
 */
@Builder
@Getter
public class Address {
    /**
     * address_line_1
     * string, max: 40 OPTIONAL
     * This alphanumeric field contains the Street/Municipality/Delegation address
     * where the Seller's business is legally registered or located (physical address only, P.O. boxes are not allowed).
     * To be used in conjunction with the Seller Street Address 1 when providing additional street address information.
     */
    @JsonProperty("address_line_1")
    String addressLine1;


    /**
     * address_line_2
     * string, max: 40 OPTIONAL
     * This alphanumeric field contains the Street/Municipality/Delegation address
     * where the Seller's business is legally registered or located (physical address only, P.O. boxes are not allowed).
     * To be used in conjunction with the Seller Street Address 1 when providing additional street address information.
     */
    @JsonProperty("address_line_2")
    String addressLine2;


    /**
     * address_line_3
     * string, max: 4 0OPTIONAL
     * This alphanumeric field contains the Street/Municipality/Delegation address
     * where the Seller's business is legally registered or located (physical address only, P.O. boxes are not allowed).
     * To be used in conjunction with the Seller Street Address 1 when providing additional street address information.
     */
    @JsonProperty("address_line_3")
    String addressLine3;


    /**
     * address_line_4
     * string, max: 40 OPTIONAL
     * This alphanumeric field contains the Street/Municipality/Delegation address
     * where the Seller's business is legally registered or located (physical address only, P.O. boxes are not allowed).
     * To be used in conjunction with the Seller Street Address 1 when providing additional street address information.
     */
    @JsonProperty("address_line_4")
    String addressLine4;

    /**
     * address_line_5
     * string, max: 40 OPTIONAL
     * This alphanumeric field contains the Street/Municipality/Delegation address
     * where the Seller's business is legally registered or located (physical address only, P.O. boxes are not allowed).
     * To be used in conjunction with the Seller Street Address 1 when providing additional street address information.
     */
    @JsonProperty("address_line_5")
    String addressLine5;

    /**
     * city_name
     * string, max: 40 REQUIRED
     * This alphanumeric field contains the name of the city where the Seller's business is legally registered or physically
     * located (physical address only, P.O. boxes are not allowed). If the city name is more than 40 characters,
     * use proper and meaningful abbreviations, when possible. Do not truncate.
     */
    String cityName;

    /**
     * region_code
     * string, max: 6 REQUIRED
     * REQUIRED: U.S. Region, Canada, and Mexico.
     * <p>
     * OPTIONAL: All other regions and/or countries.
     * <p>
     * This alphanumeric field contains the region code that corresponds to the state,
     * province, or other country subdivision where the Seller's business is legally registered or physically located
     * (physical address only, P.O. boxes are not allowed).
     * <p>
     * Valid values are found in the Region Codes Guide.
     */
    String regionCode;

    /**
     * postal_code
     * string, max: 15 REQUIRED
     * REQUIRED: All countries except those listed as optional.
     * <p>
     * OPTIONAL: Hong Kong and Thailand.
     * <p>
     * This alphanumeric field contains the Postal Code for where the Seller's
     * business is legally registered or physically located (physical address only, P.O. boxes are not allowed).
     */
    String postalCode;

    /**
     * country_code
     * string, max: 2 REQUIRED
     * This field contains a two-character alphabetic country code, where the Seller's business is legally registered or
     * physically located (physical address only, P.O. boxes are not allowed). For example,
     * the ISO alphanumeric code for USA is US. Valid values are found in the Country Codes Guide.
     */
    String countryCode;


}
