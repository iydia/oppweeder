const username = arguments[0];

const metadata = {
  FOLLOWERS: "followers",
  FOLLOWINGS: "followings",
  OPPS: "opps",
};

let followers = [];
let followings = [];
let opps = [];

const FOLLOWERS_QUERY_URL = `https://www.instagram.com/graphql/query/?query_hash=c76146de99bb02f6415203be841dd25a`;
const FOLLOWINGS_QUERY_URL = `https://www.instagram.com/graphql/query/?query_hash=d04b0a864b4b54837c0d870b0e77e076`;

/**
 * Fetches a list (followers or followings) from Instagram's GraphQL API.
 * @param {string} queryUrl - The Instagram GraphQL API endpoint for fetching the list.
 * @param {string} userId - The ID of the Instagram user whose data is being retrieved.
 * @param {string} metadataKey - A label used for logging and debugging (e.g., "followers", "followings").
 * @param {string} edgeKey - The GraphQL key for accessing either followers ("edge_followed_by") or followings ("edge_follow").
 * @returns {Promise<Array<{username: string, full_name: string}>>} - A promise that resolves to an array of user objects containing `username` and `full_name`.
 */
async function scrapeList(queryUrl, userId, metadataKey, edgeKey) {
  let nextCursor = null;
  let hasNextPage = true;
  const results = [];

  while (hasNextPage) {
    try {
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

(async() => {
  try {
    // Fetch user ID using the passed username
    const response = await fetch(`https://www.instagram.com/web/search/topsearch/?query=${username}`);
    const data = await response.json();
    const matchingUser = data.users.map(userObj => userObj.user).find(user => user.username === username);
    const userId = matchingUser ? matchingUser.pk : null;  // case user not found

    // Get follower and following lists
    const followers = await scrapeList(FOLLOWERS_QUERY_URL, userId, metadata.FOLLOWERS, "edge_followed_by");  
    const followings = await scrapeList(FOLLOWINGS_QUERY_URL, userId, metadata.FOLLOWINGS, "edge_follow");

    // Determine opps (people you follow who don't follow you back)
    opps = followings.filter((following) => {
      return !followers.find(
        (follower) => follower.username === following.username
      );
    });
    console.log({ [metadata.OPPS]: opps });

    // Return the opps array as a JSON string so Selenium can capture it
    return JSON.stringify(opps);

  } catch (e) {
    console.error({ "Process failed with Error": e });
  }

})();
