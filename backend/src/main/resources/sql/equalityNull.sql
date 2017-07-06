CREATE OR REPLACE FUNCTION equalityNull(param1 text,param2 text)
  RETURNS boolean  AS
$BODY$

Begin

     if param2 = 'null' then return true;

     elsif(param1 = param2) then return true;

     end if;

    return false;
    End
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
