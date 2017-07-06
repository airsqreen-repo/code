CREATE OR REPLACE FUNCTION tosearch(name character varying)
  RETURNS character varying AS
$BODY$

Begin

     name = upper(name);
     name = replace(name, 'Ç', 'C');
     name = replace(name, 'Ş', 'S');
     name = replace(name, 'Ü', 'U');
     name = replace(name, 'Ğ', 'G');
     name = replace(name, 'Ö', 'O');
     name = replace(name, 'İ', 'I');

    return upper(name);
    End
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION tosearch(character varying)
  OWNER TO postgres;
