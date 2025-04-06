(function(username, callback) {
    console.log("Starting oppweeder script for username: " + username);
  
    const metadata = {
      FOLLOWERS: "followers",
      FOLLOWINGS: "followings",
      OPPS: "opps",
    };
  
    async function scrapeList(queryUrl, userId, metadataKey, edgeKey) {
      let nextCursor = null;
      let hasNextPage = true;
      const results = [];
      while (hasNextPage) {
        try {
          console.log("Fetching " + metadataKey + " with cursor: " + nextCursor);
          const response = await fetch(
            queryUrl + "&variables=" + encodeURIComponent(
              JSON.stringify({
                id: userId,
                include_reel: true,
                fetch_mutual: true,
                first: 50,
                after: nextCursor,
              })
            )
          );
          console.log("Response received for " + metadataKey);
          const data = await response.json();
          const pageInfo = data.data.user[edgeKey].page_info;
          hasNextPage = pageInfo.has_next_page;
          nextCursor = pageInfo.end_cursor;
          results.push(
            ...data.data.user[edgeKey].edges.map(({ node }) => ({
              username: node.username,
              full_name: node.full_name,
            }))
          );
        } catch (error) {
          console.error(`Error fetching ${metadataKey}:`, error);
          break;
        }
      }
      return results;
    }
  
    (async function() {
      try {
        console.log("Before fetching user ID for " + username);
        const response = await fetch(`https://www.instagram.com/web/search/topsearch/?query=${username}`);
        console.log("User search fetch complete.");
        const data = await response.json();
        console.log("Search response data:", data);
        const matchingUser = data.users.map(userObj => userObj.user).find(user => user.username === username);
        if (!matchingUser) {
          console.error("No matching user found for: " + username);
          callback(null);
          return;
        }
        const userId = matchingUser.pk;
        console.log("Fetched user ID: " + userId);
  
        console.log("Fetching followers...");
        const followers = await scrapeList(
          "https://www.instagram.com/graphql/query/?query_hash=c76146de99bb02f6415203be841dd25a",
          userId,
          metadata.FOLLOWERS,
          "edge_followed_by"
        );
        console.log("Followers count:", followers.length);
  
        console.log("Fetching followings...");
        const followings = await scrapeList(
          "https://www.instagram.com/graphql/query/?query_hash=d04b0a864b4b54837c0d870b0e77e076",
          userId,
          metadata.FOLLOWINGS,
          "edge_follow"
        );
        console.log("Followings count:", followings.length);
  
        // Determine opps (people you follow who don't follow you back)
        const opps = followings.filter(following => {
          return !followers.find(follower => follower.username === following.username);
        });
        console.log({ [metadata.OPPS]: opps });
  
        // When done, call the callback with the result
        callback(JSON.stringify(opps));
      } catch (e) {
        console.error("Process failed with error:", e);
        callback(null);
      }
    })();
  })(arguments[0], arguments[arguments.length - 1]);