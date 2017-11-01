DROP VIEW public.VIEW_ACTIVE_DEVICES;

CREATE OR REPLACE VIEW public.VIEW_ACTIVE_DEVICES AS

  (SELECT   ad.id as device_id from air_devices ad)
  except
  (select DISTINCT (aspe.device_id) as device_id from
    air_events ae ,
    air_sistem9_push_events aspe
  where
    ae.id = aspe.id
    and  now() - interval '20' second  < ae.update_date
    and ae.event_status in ('DONE'));




CREATE OR REPLACE VIEW public.SSP_VIEW_COUNT AS
  (
SELECT vc.id as id ,aaa.ssp_price,aaa.device_id,aaa.action_id,aaa.ssp_device_id,aaa.platform_user_id
FROM air_VIEW_COUNT vc
  , (
      SELECT
        CAMDEVSEC.device_id AS device_id,
        c.external_id       AS campaign_id,
        s.external_id       AS section_id,
        CAMDEVSEC.ssp_price as ssp_price,
        CAMDEVSEC.action_id AS action_id ,
        ad.external_device_id as ssp_device_id,
        pu.id as platform_user_id
      FROM
        (SELECT
           cs.device_id   AS device_id,
           cs.campaign_id AS campaign_id,
           cs.SECTION_ID  AS section_id,
           cs.ssp_price as ssp_price,
           cs.action_id as action_id
         FROM air_CAMPAIGN_SECTIONS cs)
        CAMDEVSEC, air_campaigns c, air_sections s, air_devices ad, air_PLATFORM_USERS pu
      WHERE CAMDEVSEC.campaign_id = c.id AND CAMDEVSEC.section_id = s.id and ad.id = CAMDEVSEC.device_id and pu.id = ad.platform_user_id ) aaa
WHERE
  vc.section_id :: VARCHAR = aaa.section_id :: VARCHAR
  AND
  vc.campaign_id :: VARCHAR = aaa.campaign_id :: VARCHAR
  AND vc.device_id :: VARCHAR = aaa.device_id :: VARCHAR)


CREATE OR REPLACE VIEW public.SSP_VIEW_COUNT_LOG AS
  (

    SELECT
      vcl.update_date,
      vsvc.*
    FROM SSP_VIEW_COUNT vsvc,
      AIR_VIEW_COUNT_LOG vcl
    WHERE vsvc.id = vcl.view_count_id
  );


ALTER TABLE public.VIEW_ACTIVE_DEVICES
  OWNER TO postgres;

ALTER TABLE public.SSP_VIEW_COUNT
  OWNER TO postgres;


ALTER TABLE public.SSP_VIEW_COUNT_LOG
  OWNER TO postgres;
