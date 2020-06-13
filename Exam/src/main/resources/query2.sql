SELECT *, COUNT(satelites.id) AS satelites_count
FROM planets
         LEFT JOIN satelites ON planets.id = satelites.planet_id
GROUP BY planets.id, satelites.id
ORDER BY planets.radius, satelites_count DESC;