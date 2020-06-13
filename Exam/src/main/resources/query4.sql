SELECT galaxies.name, SUM(temperature) AS TEMP
FROM galaxies
         INNER JOIN planets p on galaxies.id = p.galaxy_id
GROUP BY galaxies.name
ORDER BY TEMP DESC
LIMIT 1;