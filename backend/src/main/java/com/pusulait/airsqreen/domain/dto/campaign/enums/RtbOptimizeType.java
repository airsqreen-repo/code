package com.pusulait.airsqreen.domain.dto.campaign.enums;

/**
 * Created by benan on 7/9/2017.
 */
public enum RtbOptimizeType {

    NONE, CTR, CVR


    /**   none: No optimization.
     *    ctr: Use minimum_ctr_percentage. Should be a float between 0.1 and 1.0 (blank allowed).
     CTR optimization will adjust the campaigns CPM bid according to the probability of a click.
     *    cvr: Use rtb_optimize_conversion_id. CVR optimization will adjust the campaigns CPM bid according to the probability of a conversion.*/
}
