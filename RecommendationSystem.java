import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.recommender.*;
import org.apache.mahout.cf.taste.impl.recommender.*;
import org.apache.mahout.cf.taste.impl.neighborhood.*;
import org.apache.mahout.cf.taste.impl.similarity.*;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.model.PreferenceArray;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class RecommendationSystem {

    public static void main(String[] args) {
        try {
            // Load data model (from the CSV file)
            DataModel model = new FileDataModel(new File("user_ratings.csv"));

            // Similarity model (cosine similarity)
            ItemSimilarity similarity = new PearsonCorrelationSimilarity(model);

            // Neighborhood model (user-based)
            UserNeighborhood neighborhood = new NearestNUserNeighborhood(3, similarity, model);

            // Recommender system (item-based collaborative filtering)
            Recommender recommender = new GenericUserBasedRecommender(model, neighborhood, similarity);

            // Get recommendations for user with ID 1
            List<RecommendedItem> recommendations = recommender.recommend(1, 3); // Recommend 3 items for user 1

            // Print recommended items
            System.out.println("Recommended items for user 1:");
            for (RecommendedItem item : recommendations) {
                System.out.println("ItemID: " + item.getItemID() + " - Value: " + item.getValue());
            }

        } catch (IOException | TasteException e) {
            e.printStackTrace();
        }
    }
}
