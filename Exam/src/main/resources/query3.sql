SELECT *
FROM planets
         LEFT JOIN satelites ON planets.id = satelites.planet_id
         INNER JOIN galaxies ON galaxies.id = planets.galaxy_id
GROUP BY planets.id, galaxies.id, satelites.id
ORDER BY count(satelites.id)
LIMIT 1;