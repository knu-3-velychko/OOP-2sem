SELECT p.name as pname, s.name as sname
FROM planets p
         LEFT JOIN satelites s ON p.id = s.planet_id
         INNER JOIN galaxies g ON p.galaxy_id = g.id
WHERE has_life = true
  AND g.name=?;