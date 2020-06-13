SELECT *
FROM planets
         LEFT JOIN satelites ON planets.id = satelites.planet_id
         INNER JOIN galaxies ON planets.galaxy_id = galaxies.id
WHERE has_life = true
  AND galaxies.name=?;