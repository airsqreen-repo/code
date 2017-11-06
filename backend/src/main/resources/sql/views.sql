DROP VIEW public.VIEW_ACTIVE_DEVICES;

CREATE OR REPLACE VIEW public.VIEW_ACTIVE_DEVICES AS

  (SELECT ad.id AS device_id
   FROM air_devices ad)
  EXCEPT
  (SELECT DISTINCT (aspe.device_id) AS device_id
   FROM
     air_events ae,
     air_sistem9_push_events aspe
   WHERE
     ae.id = aspe.id
     AND now() - INTERVAL '20' SECOND < ae.update_date
     AND ae.event_status IN ('DONE'));


CREATE OR REPLACE VIEW public.SSP_VIEW_COUNT AS
  (
    SELECT
      vc.id AS id,
      aaa.ssp_price,
      aaa.device_id,
      aaa.action_id,
      aaa.ssp_device_id,
      aaa.platform_user_id,
      aaa.device_name
    FROM air_VIEW_COUNT vc
      , (
          SELECT
            CAMDEVSEC.device_id   AS device_id,
            c.external_id         AS campaign_id,
            s.external_id         AS section_id,
            CAMDEVSEC.ssp_price   AS ssp_price,
            CAMDEVSEC.action_id   AS action_id,
            ad.external_device_id AS ssp_device_id,
            pu.id                 AS platform_user_id,
            ad.name as device_name
          FROM
            (SELECT
               cs.device_id   AS device_id,
               cs.campaign_id AS campaign_id,
               cs.SECTION_ID  AS section_id,
               cs.ssp_price   AS ssp_price,
               cs.action_id   AS action_id
             FROM air_CAMPAIGN_SECTIONS cs)
            CAMDEVSEC, air_campaigns c, air_sections s, air_devices ad, air_PLATFORM_USERS pu
          WHERE CAMDEVSEC.campaign_id = c.id AND CAMDEVSEC.section_id = s.id AND ad.id = CAMDEVSEC.device_id AND
                pu.id = ad.platform_user_id) aaa
    WHERE
      vc.section_id :: VARCHAR = aaa.section_id :: VARCHAR
                     AND
    vc.campaign_id :: VARCHAR = aaa.campaign_id :: VARCHAR
                                                AND vc.device_id :: VARCHAR = aaa.device_id :: VARCHAR);

CREATE OR REPLACE VIEW public.SSP_VIEW_COUNT_LOG AS
  (

    SELECT
      vcl.update_date,
      vsvc.*
    FROM SSP_VIEW_COUNT vsvc,
      AIR_VIEW_COUNT_LOG vcl
    WHERE vsvc.id = vcl.view_count_id
  );

CREATE OR REPLACE VIEW public.EVENT_RUN_REPORT AS
  (

    SELECT
      ae.event_status AS event_Status,
      ae.expire_date  AS expire_Date,
      ae.run_date     AS run_Date,
      c.id            AS campaign_Id,
      cs.id           as campaign_Section_Id,
      ae.event_type as event_Type,
      cs.ssp_price as ssp_Price,
      c.price as price,
      ae.id as id,
      c.name as campaign_name,
      d.name as device_name,
      d.id as device_id
    FROM air_SISTEM9_PUSH_EVENTS spe, air_events ae, air_campaign_sections cs, air_campaigns c, air_devices d
    WHERE spe.id = ae.id AND spe.campaign_section_id = cs.id AND cs.campaign_id = c.id and cs.device_id = d.id
  );


ALTER TABLE public.VIEW_ACTIVE_DEVICES
  OWNER TO postgres;

ALTER TABLE public.SSP_VIEW_COUNT
  OWNER TO postgres;

ALTER TABLE public.SSP_VIEW_COUNT_LOG
  OWNER TO postgres;

ALTER TABLE public.EVENT_RUN_REPORT
  OWNER TO postgres;