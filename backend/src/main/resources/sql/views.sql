DROP VIEW public.VIEW_ACTIVE_DEVICES;

CREATE OR REPLACE VIEW public.VIEW_ACTIVE_DEVICES AS
  select aspe.id,aspe.device_id from
    air_events ae ,
    air_sistem9_push_events aspe
  where
    ae.id = aspe.id
    and EXTRACT(SECOND FROM CURRENT_TIMESTAMP - ae.update_date ) < 20
    and ae.data_status = 'DONE';


ALTER TABLE public.VIEW_ACTIVE_DEVICES
  OWNER TO postgres;