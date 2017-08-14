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


ALTER TABLE public.VIEW_ACTIVE_DEVICES
  OWNER TO postgres;