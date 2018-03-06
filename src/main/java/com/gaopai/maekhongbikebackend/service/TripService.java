package com.gaopai.maekhongbikebackend.service;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.gaopai.maekhongbikebackend.bean.trip.CreateTripBean;
import com.gaopai.maekhongbikebackend.bean.trip.CreateTripImageBean;
import com.gaopai.maekhongbikebackend.bean.trip.UpdateTripBean;
import com.gaopai.maekhongbikebackend.bean.trip.UpdateTripImageBean;
import com.gaopai.maekhongbikebackend.domain.Trip;
import com.gaopai.maekhongbikebackend.domain.TripImage;
import com.gaopai.maekhongbikebackend.domain.Users;
import com.gaopai.maekhongbikebackend.exception.DataFormatException;
import com.gaopai.maekhongbikebackend.repository.impl.TripImageRepositoryService;
import com.gaopai.maekhongbikebackend.repository.impl.TripRepositoryService;
import com.gaopai.maekhongbikebackend.utils.Utility;
import com.gaopai.maekhongbikebackend.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class TripService {
    private static final Logger log = LoggerFactory.getLogger(TripService.class);


    @Autowired
    private TripRepositoryService tripRepositoryService;

    @Autowired
    private TripImageRepositoryService tripImageRepositoryService;

    @Transactional
    public ObjectNode createTrip(CreateTripBean body) throws Exception {
        Trip trip = new Trip();
        trip.setTrip_name(body.getTrip_name());
        trip.setDescription(body.getDescription());

        try {
            trip = tripRepositoryService.save(trip);

            return createTripJson(trip);
        } catch (DataFormatException e) {
            throw new DataFormatException("create trip fail.");
        }

    }

    @Transactional
    public ObjectNode createTripImage(CreateTripImageBean body, Long trip_id) throws Exception {
        Trip trip = tripRepositoryService.find(trip_id);
        List<TripImage> tripImages = new ArrayList<>();

        TripImage tripImage = new TripImage();
        tripImage.setImage_url(body.getImage_url());
        tripImage.setFilename(body.getFilename());
        tripImage.setTrip(trip);

        try {
            tripImage = tripImageRepositoryService.save(tripImage);

            if (tripImage != null) {
                tripImages = tripImageRepositoryService.findByTrip_Id(trip_id);
            }

            return createTripJson(trip, tripImages);
        } catch (DataFormatException e) {
            throw new DataFormatException("create trip image fail.");
        }

    }


    @Transactional
    public ObjectNode updateTripImage(UpdateTripImageBean body, Long trip_id) throws Exception {
        Trip trip = tripRepositoryService.find(trip_id);
        List<TripImage> tripImages = tripImageRepositoryService.findByTrip_Id(trip_id);

        trip.setImage_preview(body.getImage_url());
        trip.setFilename(body.getFilename());

        try {
            tripRepositoryService.update(trip);

            return createTripJson(trip, tripImages);
        } catch (DataFormatException e) {
            throw new DataFormatException("update trip image fail.");
        }

    }

    @Transactional
    public ObjectNode updateTrip(UpdateTripBean body, Long trip_id) throws Exception {
        Trip trip = tripRepositoryService.find(trip_id);

        trip.setTrip_name(body.getTrip_name());
        trip.setDescription(body.getDescription());

        try {
            tripRepositoryService.update(trip);
            List<TripImage> tripImages = tripImageRepositoryService.findByTrip_Id(trip_id);

            return createTripJson(trip, tripImages);
        } catch (DataFormatException e) {
            throw new DataFormatException("update trip fail.");
        }

    }

    public ObjectNode getTripById(Long id) throws Exception {
        Trip trip = tripRepositoryService.find(id);
        List<TripImage> tripImages = tripImageRepositoryService.findByTrip_Id(id);
        Utility.verifiedIsNullObject(trip, "trip");

        ObjectNode jsonNodes = createTripJson(trip, tripImages);

        return jsonNodes;
    }

    public ObjectNode deleteTripImageById(Long trip_image_id) throws Exception {
        TripImage tripImage = tripImageRepositoryService.find(trip_image_id);

        Trip trip = tripRepositoryService.find(tripImage.getTrip().getId());


        Utility.verifiedIsNullObject(tripImage, "tripImage");

        if (tripImage != null) {
            try {

                tripImageRepositoryService.delete(trip_image_id);

                List<TripImage> tripImages = tripImageRepositoryService.findByTrip_Id(trip.getId());
                ObjectNode jsonNodes = createTripJson(trip, tripImages);
                return jsonNodes;
            } catch (DataFormatException e) {
                throw new DataFormatException("delete trip image fail");
            }
        } else {
            throw new DataFormatException("invalid trip image id");
        }
    }

    public ObjectNode deleteTripById(Long trip_id) throws Exception {
        Trip trip = tripRepositoryService.find(trip_id);

        Utility.verifiedIsNullObject(trip, "trip");

        List<TripImage> tripImages = tripImageRepositoryService.findByTrip_Id(trip.getId());

        if (trip != null) {
            try {

                tripRepositoryService.delete(trip_id);

                ObjectNode jsonNodes = createTripJson(trip, tripImages);
                return jsonNodes;
            } catch (DataFormatException e) {
                throw new DataFormatException("delete trip fail");
            }
        } else {
            throw new DataFormatException("invalid trip id");
        }
    }


    public ArrayNode getAllTrip(Users user) throws Exception {
        List<Trip> trips = tripRepositoryService.findAll();
        Utility.verifiedIsNullObject(trips, "trips");
        List<TripImage> tripImages = new ArrayList<>();
        ArrayNode arrayNode = new ArrayNode(JsonNodeFactory.instance);
        if (trips.size() > 0) {
            for (Trip trip : trips) {
                tripImages = tripImageRepositoryService.findByTrip_Id(trip.getId());
                arrayNode.add(createTripJson(trip, tripImages));
            }
        }
        return arrayNode;
    }

    public ArrayNode getTripImageByTripId(Users user, Long trip_id) throws Exception {
        List<TripImage> tripImages = tripImageRepositoryService.findByTrip_Id(trip_id);
        Utility.verifiedIsNullObject(tripImages, "tripImages");

        ArrayNode arrayNode = new ArrayNode(JsonNodeFactory.instance);
        if (tripImages.size() > 0) {
            for (TripImage tripImage : tripImages) {
                arrayNode.add(createTripImageJson(tripImage));
            }
        }
        return arrayNode;
    }

    private ObjectNode createTripJson(Trip trip) {
        ObjectNode jsonNodes = new ObjectNode(JsonNodeFactory.instance);
        jsonNodes.put("id", trip.getId());
        jsonNodes.put("trip_name", trip.getTrip_name());
        jsonNodes.put("description", trip.getDescription());
        jsonNodes.put("image_url", trip.getImage_preview());
        jsonNodes.put("filename", trip.getFilename());

        return jsonNodes;
    }

    private ObjectNode createTripJson(Trip trip, List<TripImage> tripImages) {
        ObjectNode jsonNodes = new ObjectNode(JsonNodeFactory.instance);
        jsonNodes.put("id", trip.getId());
        jsonNodes.put("trip_name", trip.getTrip_name());
        jsonNodes.put("description", trip.getDescription());
        jsonNodes.put("image_url", trip.getImage_preview());
        jsonNodes.put("filename", trip.getFilename());

        jsonNodes.set("trip_images", createTripImageArrayNode(tripImages));

        return jsonNodes;
    }

    private ObjectNode createUrlImageTripJson(Trip trip, List<TripImage> tripImages) {
        ObjectNode jsonNodes = new ObjectNode(JsonNodeFactory.instance);



        jsonNodes.set("images", createTripImageUrlArrayNode(tripImages));

        return jsonNodes;
    }

    private ArrayNode createTripImageArrayNode(List<TripImage> tripImages) {
        ArrayNode tripImageArrayNode = new ArrayNode(JsonNodeFactory.instance);
        for (TripImage tripImage : tripImages) {
            ObjectNode tripImageNode = new ObjectNode(JsonNodeFactory.instance);
            tripImageNode.put("id", tripImage.getId());
            tripImageNode.put("image_url", tripImage.getImage_url());
            tripImageNode.put("filename", tripImage.getFilename());
            tripImageArrayNode.add(tripImageNode);
        }
        return tripImageArrayNode;
    }

    private ArrayNode createTripImageUrlArrayNode(List<TripImage> tripImages) {
        ArrayNode tripImageArrayNode = new ArrayNode(JsonNodeFactory.instance);
        for (TripImage tripImage : tripImages) {
            ObjectNode tripImageNode = new ObjectNode(JsonNodeFactory.instance);
            tripImageNode.put("id", tripImage.getId());
            tripImageNode.put("src", tripImage.getImage_url());
            tripImageArrayNode.add(tripImageNode);
        }
        return tripImageArrayNode;
    }

    private ObjectNode createTripImageJson(TripImage tripImage) {
        ObjectNode jsonNodes = new ObjectNode(JsonNodeFactory.instance);
        jsonNodes.put("id", tripImage.getId());
        jsonNodes.put("image_url", tripImage.getImage_url());
        return jsonNodes;
    }
}
